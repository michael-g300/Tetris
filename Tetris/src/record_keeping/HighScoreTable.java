package record_keeping;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HighScoreTable {
    private static final String FILE_PATH = "./High_Scores.txt";
    private static final String NAME_SCORE_SEPARATOR = "%";
    private static final int MAX_NAME_LENGTH = 20;
    private static final int MAX_SCORES = 10;
    private final Path m_highscoreFilePath;
    private final PlayerScore[] m_highScores = new PlayerScore[MAX_SCORES];
    public HighScoreTable() {
        m_highscoreFilePath = Paths.get(FILE_PATH);
        createHighScoreFile();
        readFromFile();
    }
    public PlayerScore[] getPlayersScores() {
        return m_highScores;
    }

    private void readFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                final int separatorPosition = line.indexOf(NAME_SCORE_SEPARATOR);
                if (separatorPosition <= 0) {
                    break;
                }
                final String playerName = line.substring(0, separatorPosition);
                final int playerScore = Integer.valueOf(line.substring(separatorPosition + 1));
                insertPlayerIntoTable(new PlayerScore(playerName, playerScore));
                line = reader.readLine();
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        writeToFile();
    }

    private boolean insertPlayerIntoTable(final PlayerScore newPlayer) {
        for (int i = 0 ; i < m_highScores.length ; ++i) {
            if (m_highScores[i] == null) {
                m_highScores[i] = newPlayer;
                writeToFile();
                return true;
            }
            if (m_highScores[i].score() < newPlayer.score()) {
                for (int j = m_highScores.length - 1 ; j > i ; --j) {
                    m_highScores[j] = m_highScores[j - 1];
                }
                m_highScores[i] = newPlayer;
                writeToFile();
                return true;
            }
        }
        return false;
    }
    private void writeToFile() {
        StringBuilder updatedFileContent = new StringBuilder();
        for (PlayerScore player : m_highScores) {
            if (player == null) {
                break;
            }
            updatedFileContent.append(player.name());
            updatedFileContent.append(NAME_SCORE_SEPARATOR);
            updatedFileContent.append(player.score());
            updatedFileContent.append("\n");
        }
        try {
            Files.write(Paths.get(FILE_PATH), updatedFileContent.toString().getBytes());
        }
        catch (IOException e) {
            System.out.println("Unable to write to file");
            throw new RuntimeException(e);
        }
    }
    public boolean updateScores(final String playerName, final int playerScore) {
        return insertPlayerIntoTable(new PlayerScore(playerName, playerScore));
    }
    public int getLowestHighScore() {
        if (m_highScores[m_highScores.length - 1] != null) {
            return m_highScores[m_highScores.length - 1].score();
        }
        return 0;
    }
    private void createHighScoreFile() {
        if (Files.exists(m_highscoreFilePath)) {
            if (Files.isDirectory(m_highscoreFilePath) || !Files.isReadable(m_highscoreFilePath) || !Files.isWritable(m_highscoreFilePath)) {
                System.out.println("Unable to read from file.");
                throw new IllegalArgumentException();
            }
        }
        else {
            try {
                Files.createFile(m_highscoreFilePath);
                System.out.println("New file created - " + m_highscoreFilePath.toString());
            }
            catch (IOException e) {
                System.out.println("File creation failed - " + m_highscoreFilePath);
                throw new RuntimeException(e);
            }
        }
    }
}
