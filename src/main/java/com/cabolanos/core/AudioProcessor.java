package com.cabolanos.core;

import org.openimaj.audio.AudioFormat;
import org.openimaj.audio.JavaSoundAudioGrabber;
import org.openimaj.audio.features.MFCC;
import org.openimaj.audio.processor.FixedSizeSampleAudioProcessor;
import org.openimaj.vis.general.BarVisualisation;

/**
 * Created by Toshiba on 31/03/2016.
 */
public class AudioProcessor {

    public AudioProcessor() {
    }

    public void process() {
        final JavaSoundAudioGrabber jsag = new JavaSoundAudioGrabber(
                new AudioFormat(16, 44.1, 1));
        new Thread(jsag).start();
        // Let's create 30ms windows with 10ms overlap
        final FixedSizeSampleAudioProcessor fssap = new FixedSizeSampleAudioProcessor(jsag, 441 * 3, 441);
        // Create the Fourier transform processor chained to the audio decoder
        final MFCC mfcc = new MFCC(fssap);
        // Create a visualisation to show our FFT and open the window now

        final BarVisualisation bv = new BarVisualisation(400, 200);
        bv.setAxisLocation(100);
        bv.showWindow("MFCCs");
        // Loop through the sample chunks from the audio capture thread
        // sending each one through the feature extractor and displaying
        // the results in the visualisation.
        while (mfcc.nextSampleChunk() != null) {
            final double[][] mfccs = mfcc.getLastCalculatedFeatureWithoutFirst();
            bv.setData(mfccs[0]);
        }
    }
}
