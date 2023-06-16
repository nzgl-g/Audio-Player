package com.youneshabbal.Window;

import javax.swing.*;
import java.awt.*;

public class APWindow extends NCPanelAnimation {
    public JMenuBar menuBar;
    public JMenu file, edit, help;
    public JPanel mainPanel, nPanel, nnPanel, nsPanel, nePanel, nwPanel, ncPanel, sPanel, ssPanel, snPanel, snePanel, snwPanel, sncPanel;
    public JButton playButton, pauseButton, restartButton, nextButton, previousButton;
    public JProgressBar clipProgressBar;
    public JMenuItem save, load, exit;
    public JLabel snwLabel;
    public JLabel sneLabel;
    public NCPanelAnimation animation;
    
    public APWindow() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        
        ImageIcon saveicon = new ImageIcon("save.png");
        ImageIcon loadicon = new ImageIcon("sload.png");
        ImageIcon exiticon = new ImageIcon("exit.png");
        
        // Frame
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setSize(new Dimension(800, 500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        
        menuBar = new JMenuBar();
        file = new JMenu("File");
        edit = new JMenu("Edit");
        help = new JMenu("Help");
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(help);
        
        save = new JMenuItem("Save", saveicon);
        load = new JMenuItem("Load", loadicon);
        exit = new JMenuItem("Exit", exiticon);
        file.add(load);
        file.add(save);
        file.add(exit);
        
        // Main panel holds the other panels
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setSize(frame.getWidth(), frame.getHeight() - menuBar.getHeight());
        mainPanel.setBackground(new Color(110, 111, 112));
        mainPanel.setOpaque(true);
        
        // The north panel
        nPanel = new JPanel(new BorderLayout());
        
        ncPanel = new JPanel(new BorderLayout());
        ncPanel.setPreferredSize(new Dimension((int) (frame.getWidth() * 0.8), (int) (frame.getHeight() * 0.8)));
        ncPanel.setSize((int) (frame.getWidth() * 0.8), (int) (frame.getHeight() * 0.8));
        animation = new NCPanelAnimation(ncPanel, 100, Color.BLACK);
        ncPanel.add(animation, BorderLayout.CENTER);
        
        // The north east panel
        nePanel = new JPanel();
        nePanel.setPreferredSize(new Dimension((int) (frame.getWidth() * 0.1), (int) (frame.getHeight() * 0.8)));
        nePanel.setBackground(frame.getBackground());
        
        // The north west panel
        nwPanel = new JPanel();
        nwPanel.setPreferredSize(new Dimension((int) (frame.getWidth() * 0.1), (int) (frame.getHeight() * 0.8)));
        nwPanel.setBackground(frame.getBackground());
        
        // The north north panel
        nnPanel = new JPanel();
        nnPanel.setPreferredSize(new Dimension(0, (int) ((int) (frame.getHeight() * 0.8) * 0.1)));
        nnPanel.setBackground(frame.getBackground());
        
        // The north south panel
        nsPanel = new JPanel();
        nsPanel.setPreferredSize(new Dimension(0, (int) ((int) (frame.getHeight() * 0.8) * 0.1)));
        nsPanel.setBackground(frame.getBackground());
        
        // The south panel
        sPanel = new JPanel(new BorderLayout());
        sPanel.setBackground(Color.green);
        sPanel.setPreferredSize(new Dimension(frame.getWidth(), (int) (frame.getHeight() * 0.2)));
        
        // The south south panel
        ssPanel = new JPanel(new GridBagLayout());
        ssPanel.setPreferredSize(new Dimension(frame.getWidth(), (int) ((int) (frame.getHeight() * 0.2) * 0.3)));
        ssPanel.setBackground(frame.getBackground());
        
        // The south north panel
        snPanel = new JPanel(new BorderLayout());
        snPanel.setBackground(Color.CYAN);
        
        // The south north east panel
        snePanel = new JPanel();
        snePanel.setPreferredSize(new Dimension((int) (frame.getWidth() * 0.1), snPanel.getHeight()));
        snePanel.setBackground(Color.DARK_GRAY);
        snePanel.setLayout(new BorderLayout());
        
        sneLabel = new JLabel("ee");
        sneLabel.setForeground(Color.BLACK);
        sneLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        sneLabel.setHorizontalAlignment(JLabel.CENTER);
        sneLabel.setVerticalAlignment(JLabel.CENTER);
        sneLabel.setBackground(frame.getBackground());
        sneLabel.setOpaque(true);
        snePanel.add(sneLabel, BorderLayout.CENTER);
        
        // The south north west panel
        snwPanel = new JPanel();
        snwPanel.setPreferredSize(new Dimension((int) (frame.getWidth() * 0.1), snPanel.getHeight()));
        snwPanel.setBackground(Color.DARK_GRAY);
        snwPanel.setLayout(new BorderLayout());
        
        snwLabel = new JLabel("ww");
        snwLabel.setForeground(Color.BLACK);
        snwLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        snwLabel.setHorizontalAlignment(JLabel.CENTER);
        snwLabel.setVerticalAlignment(JLabel.CENTER);
        snwLabel.setBackground(frame.getBackground());
        snwLabel.setOpaque(true);
        snwPanel.add(snwLabel, BorderLayout.CENTER);
        
        // The south north center panel
        sncPanel = new JPanel(new GridLayout(5, 1));
        sncPanel.setPreferredSize(new Dimension(frame.getWidth() - ((int) (frame.getWidth() * 0.1) * 2), (int) (frame.getHeight() * 0.2) - (int) ((int) (frame.getHeight() * 0.2) * 0.3)));
        sncPanel.setBackground(frame.getBackground());
        
        // Create buttons with custom styling
        playButton = createStyledButton("           Play             ");
        pauseButton = createStyledButton("          Pause           ");
        restartButton = createStyledButton("         Restart          ");
        nextButton = createStyledButton("    Next >  ");
        previousButton = createStyledButton("< Previous");
// Progress bar
        clipProgressBar = new JProgressBar();
        clipProgressBar.setBackground(Color.WHITE); // Set the background color
        clipProgressBar.setForeground(Color.BLUE); // Set the foreground color
        clipProgressBar.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Set a border
// Set the size of the progress bar
        clipProgressBar.setPreferredSize(new Dimension(200, 20));

        // Addition
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(menuBar, BorderLayout.NORTH);
        mainPanel.add(nPanel, BorderLayout.CENTER);
        mainPanel.add(sPanel, BorderLayout.SOUTH);
        nPanel.add(nePanel, BorderLayout.EAST);
        nPanel.add(nwPanel, BorderLayout.WEST);
        nPanel.add(nsPanel, BorderLayout.SOUTH);
        nPanel.add(nnPanel, BorderLayout.NORTH);
        nPanel.add(ncPanel, BorderLayout.CENTER);
        sPanel.add(ssPanel, BorderLayout.SOUTH);
        sPanel.add(snPanel, BorderLayout.CENTER);
        
        // Set layout manager for ssPanel
        ssPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        ssPanel.add(previousButton, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        ssPanel.add(playButton, gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        ssPanel.add(pauseButton, gbc);
        gbc.gridx = 3;
        gbc.gridy = 0;
        ssPanel.add(restartButton, gbc);
        gbc.gridx = 4;
        gbc.gridy = 0;
        ssPanel.add(nextButton, gbc);
        
        snPanel.add(snePanel, BorderLayout.EAST);
        snPanel.add(snwPanel, BorderLayout.WEST);
        snPanel.add(sncPanel, BorderLayout.CENTER);
        sncPanel.add(new JPanel());
        sncPanel.add(new JPanel());
        sncPanel.add(clipProgressBar);
        frame.pack();
        frame.setVisible(true);
    }
    
    public void AStart() {
        animation.timer.start();
    }
    
    public void AStop() {
        animation.timer.stop();
    }
    
    public static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(120, 40));
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        button.setFocusable(false);
        return button;
    }
}
