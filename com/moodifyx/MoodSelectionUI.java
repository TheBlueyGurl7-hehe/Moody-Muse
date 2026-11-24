package com.moodifyx;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.*;



public class MoodSelectionUI extends JFrame{
    private final MoodRepository repository = new MoodRepository();

    public MoodSelectionUI(){
        setTitle("Moodifyx");
        setSize(800,450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setUpUI();
        setVisible(true);

    }
    
    private void setUpUI(){
        Set<String> moods = repository.getAllMood();
        JComboBox<String> moodDropdown = new JComboBox<>(moods.toArray(new String[0]));
        JButton detectMoodBtn = new JButton("Recommend Music");

        detectMoodBtn.addActionListener(e ->{
            String mood = (String) moodDropdown.getSelectedItem();

            OUTER:
            while (true) {
                List<Song> songs = repository.getSongsForMood(mood);
                Song selected = songs.get(ThreadLocalRandom.current().nextInt(songs.size()));
                SongPlayer.play(selected.getfilePath());
               
                Object[] options = {"Another Song", "Change Mood", "Stop Music and Exit"};
                int choice = JOptionPane.showOptionDialog(
                        this,
                        "Now playing : " + selected.getTitle() + "\nMood : " + mood,
                        "Your Mood Music",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]
                );
                SongPlayer.stop();
                
                switch (choice) {
                    case JOptionPane.YES_OPTION -> {
                        continue;
                    }
                    case JOptionPane.NO_OPTION -> {
                        break OUTER;
                    }
                    default -> System.exit(0);
                }
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel("Select Your Mood: "));
        panel.add(moodDropdown);
        panel.add(detectMoodBtn);

        add(panel);
    }
}