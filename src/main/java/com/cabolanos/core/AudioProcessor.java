package com.cabolanos.core;

import org.openimaj.audio.AudioFormat;
import org.openimaj.audio.JavaSoundAudioGrabber;
import org.openimaj.audio.features.MFCC;
import org.openimaj.audio.processor.FixedSizeSampleAudioProcessor;
import org.openimaj.vis.general.BarVisualisation;

import javax.swing.*;

/**
 * Created by Toshiba on 31/03/2016.
 */
public class AudioProcessor {

    public AudioProcessor() {
    }

    public void process() {
        JavaSoundAudioGrabber soundAudioGrabber = new JavaSoundAudioGrabber(new AudioFormat(16, 44.1, 1));
        Thread t = new Thread(soundAudioGrabber);
        t.start();
        FixedSizeSampleAudioProcessor sampleAudioProcessor = new FixedSizeSampleAudioProcessor(soundAudioGrabber, 441 * 3, 441);
        MFCC mfcc = new MFCC(sampleAudioProcessor);
        BarVisualisation visualisation = new BarVisualisation(500, 300);
        visualisation.setAxisLocation(100);
        JFrame window = visualisation.showWindow("Test de datos");
        window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        while (mfcc.nextSampleChunk() != null) {
            double[][] featureList = mfcc.getLastCalculatedFeatureWithoutFirst();
            visualisation.setData(featureList[0]);
        }
    }
}
