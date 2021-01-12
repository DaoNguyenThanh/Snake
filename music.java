/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Snake;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class music {
    
    private static Sequencer sequencer;

    static {
       try {
            //MidiDevice device = MidiSystem.getMidiDevice(MidiSystem.getMidiDeviceInfo()[0]);
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static Map<String, Sequence> sequencesCache = new HashMap<String, Sequence>();
    
   public static void play(String music) {
        try {
            Sequence sequence = sequencesCache.get(music);
           if (sequence == null) {
                String resource = "/Snake/music/world_" + music + ".mid";
                InputStream is = music.class.getResourceAsStream(resource);
                sequence = MidiSystem.getSequence(is);
               sequencesCache.put(music, sequence);
           }
            sequencer.setSequence(sequence);
            sequencer.setLoopCount(999999);
            sequencer.start();
        } catch (Exception e) {
            System.out.println(e);
        }        
    }   
}


