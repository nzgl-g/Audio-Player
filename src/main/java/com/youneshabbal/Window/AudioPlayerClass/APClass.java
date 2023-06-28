package com.youneshabbal.Window.AudioPlayerClass;
import com.youneshabbal.Window.APWindow;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.prefs.Preferences;

public class APClass extends APWindow implements ActionListener {
    private static final String LAST_PATH_KEY = "lastPath";
    private Clip clip;
    private AudioInputStream audio;
    private int index = 1;
    private String pathname = "src\\main\\java\\Resources\\Dont-delete-me !.txt";
    private Thread thread;
    public APClass() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        File file1 = new File(pathname);
        if (!file1.exists()) {
            file1.createNewFile();
            fileChooser();
        }
        audio = AudioSystem.getAudioInputStream(new File(getAudioPath(index)));
        clip = AudioSystem.getClip();
        clip.open(audio);
        clip.start();
        titleLabel.setText(getFileName(getAudioPath(index)));
        AStart();
        startProgressBar();
        SwingUtilities.invokeLater(() -> {
            sneLabel.setText(Timer(clip.getMicrosecondLength()));
            repaint();
        });
        playButton.addActionListener(this);
        pauseButton.addActionListener(this);
        restartButton.addActionListener(this);
        nextButton.addActionListener(this);
        previousButton.addActionListener(this);
        load.addActionListener(this);
        clipProgressBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int mousePosition = e.getX();
                long clipLength = clip.getMicrosecondLength();
                clip.setMicrosecondPosition((mousePosition * clipLength) / clipProgressBar.getWidth());
            }
        });
        if (!clip.isRunning()) {
            try {
                index++;
                audio = AudioSystem.getAudioInputStream(new File(getAudioPath(index)));
                clip.start();
                AStart();
            } catch (UnsupportedAudioFileException ex) {
                JOptionPane.showMessageDialog(null, "File not Supported", "ERROR", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        thread = new Thread(() -> {
            do {
                long clipmicrosecondposition = clip.getMicrosecondPosition();
                snwLabel.setText(Timer(clipmicrosecondposition));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } while (clip.getMicrosecondPosition() < clip.getMicrosecondLength());
        });
        thread.start();
    }
    public String getAudioPath(int wantedLine) {
        String audioPaths = pathname;
        
        try {
            BufferedReader lineReader = new BufferedReader(new FileReader(audioPaths));
            String audioPath;
            int currentLine = 1;
            String path = null;
            while ((audioPath = lineReader.readLine()) != null) {
                if (wantedLine == currentLine) {
                    path = audioPath;
                }
                currentLine++;
            }
            lineReader.close();
            return path;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public int getPathsCounter() throws IOException {
        File file = new File(pathname);
        BufferedReader lineReader = new BufferedReader(new FileReader(file));
        int linesNumber = 0;
        while ((lineReader.readLine() != null)) {
            linesNumber++;
        }
        return linesNumber;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) {
            clip.start();
            AStart();
            startProgressBar();
        } else if (e.getSource() == pauseButton) {
            clip.stop();
            AStop();
        } else if (e.getSource() == restartButton) {
            clip.setFramePosition(0);
            clip.start();
            AStart();
            startProgressBar();
        } else if (e.getSource() == nextButton) {
            try {
                if (index + 1 <= getPathsCounter()) {
                    clip.close();
                    index++;
                    audio = AudioSystem.getAudioInputStream(new File(getAudioPath(index)));
                    clip.open(audio);
                    clip.start();
                    titleLabel.setText(getFileName(getAudioPath(index)));
                    AStart();
                    startProgressBar();
                    SwingUtilities.invokeLater(() -> {
                        sneLabel.setText(Timer(clip.getMicrosecondLength()));
                        repaint();
                    });
                } else {
                    System.out.println("There is no next audio.");
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (UnsupportedAudioFileException ex) {
                JOptionPane.showMessageDialog(null, "File not Supported", "ERROR", JOptionPane.ERROR_MESSAGE);
            } catch (LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == previousButton) {
            try {
                if (index - 1 > 0) {
                    clip.close();
                    index--;
                    audio = AudioSystem.getAudioInputStream(new File(getAudioPath(index)));
                    clip.open(audio);
                    clip.start();
                    titleLabel.setText(getFileName(getAudioPath(index)));
                    AStart();
                    startProgressBar();
                    SwingUtilities.invokeLater(() -> {
                        sneLabel.setText(Timer(clip.getMicrosecondLength()));
                        repaint();
                    });
                } else {
                    System.out.println("There is no previous audio.");
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (UnsupportedAudioFileException ex) {
                JOptionPane.showMessageDialog(null, "File not Supported", "ERROR", JOptionPane.ERROR_MESSAGE);
            } catch (LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == load) {
            fileChooser();
            clip.close();
            try {
                audio = AudioSystem.getAudioInputStream(new File(getAudioPath(index)));
                clip.open(audio);
                clip.start();
                titleLabel.setText(getFileName(getAudioPath(index)));
                AStart();
                startProgressBar();
                SwingUtilities.invokeLater(() -> {
                    sneLabel.setText(Timer(clip.getMicrosecondLength()));
                    repaint();
                });
            } catch (UnsupportedAudioFileException ex) {
                throw new RuntimeException(ex);
            } catch (LineUnavailableException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    public void startProgressBar() {
        Thread progressThread = new Thread(() -> {
            int clipLength = (int) (clip.getMicrosecondLength() / 1_000_000);
            clipProgressBar.setMaximum(clipLength);
            while (clip.getMicrosecondPosition() < clip.getMicrosecondLength()) {
                int progressValue = (int) (clip.getMicrosecondPosition() / 1_000_000);
                SwingUtilities.invokeLater(() -> clipProgressBar.setValue(progressValue));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            clipProgressBar.setValue(clipLength);
        });
        progressThread.start();
    }
    public void fileChooser() {
        JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        chooser.setDialogTitle("Select only WAV files! :D");
        Preferences preferences = Preferences.userRoot().node(FileChooserExample.class.getName());
        String lastPath = preferences.get(LAST_PATH_KEY, null);
        if (lastPath != null) {
            chooser.setCurrentDirectory(new File(lastPath));
        } else {
            chooser.setCurrentDirectory(FileSystemView.getFileSystemView().getHomeDirectory());
        }
        FileNameExtensionFilter wavFilter = new FileNameExtensionFilter("Only WAV Files", "wav");
        chooser.setFileFilter(wavFilter);
        chooser.setMultiSelectionEnabled(true);
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.setApproveButtonText("Select");
        
        chooser.setDragEnabled(true);
        int chosenFiles = chooser.showOpenDialog(null);
        if (chosenFiles == JFileChooser.APPROVE_OPTION) {
            File[] selectedFiles = chooser.getSelectedFiles();
            try {
                File outputPathFile = new File(pathname);
                if (outputPathFile.exists()) {
                    outputPathFile.delete();
                }
                outputPathFile.createNewFile();
                FileWriter writer = new FileWriter(outputPathFile, true);
                for (File file : selectedFiles) {
                    writer.write(file.getAbsolutePath() + System.lineSeparator());
                }
                writer.close();
                String currentPath = chooser.getCurrentDirectory().getAbsolutePath();
                preferences.put(LAST_PATH_KEY, currentPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public String Timer(long clipmicrosecondlengh) {
        LocalTime time = LocalTime.ofSecondOfDay(clipmicrosecondlengh / 1000000);
        long houronmicrosecond = 3600000000L;
        if (clipmicrosecondlengh < houronmicrosecond) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm:ss");
            return time.format(formatter);
        } else if (clipmicrosecondlengh >= 3600000000L) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            return time.format(formatter);
        }
        return null;
    }
    public static String getFileName(String filePath) {
        Path path = Paths.get(filePath);
        String fileNameWithExtension = path.getFileName().toString();
        int dotIndex = fileNameWithExtension.lastIndexOf(".");
        if (dotIndex > 0) {
            return fileNameWithExtension.substring(0, dotIndex);
        }
        return fileNameWithExtension;
    }
}
