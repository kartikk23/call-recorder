package com.kartik.callrecorder.threading;

public class RecordRunnable implements Runnable {
    public final static int frequency = 44100;
    private boolean recordWhileTrue = true;
    final private Service service;
    final private File file;

    public RecordRunnable(Service service, File file) {
        this.service = service;
        this.file = file;
    }

    @Override
    public void run() {
        AudioRecord record = null;
        OutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(file));
            final int minBufferSize = AudioRecord.getMinBufferSize(frequency, AudioFormat.CHANNEL_IN_MONO,
                    AudioFormat.ENCODING_PCM_16BIT);
            record = new AudioRecord(MediaRecorder.AudioSource.VOICE_CALL,
                    frequency, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, minBufferSize);
            short buffer[] = new short[minBufferSize];
            byte[] byteBuffer = new byte[minBufferSize * 2];

            record.startRecording();

            while (recordWhileTrue) {
                final int bufferRead = record.read(buffer, 0, minBufferSize);
                ByteBuffer.wrap(byteBuffer, 0, bufferRead * 2).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(buffer, 0, bufferRead);
                out.write(byteBuffer, 0, bufferRead * 2);
            }

            record.stop();
            out.close();
        } catch ( Exception e1) {
            e1.printStackTrace();
            if(record!=null){
                record.stop();
            }
            if(out!=null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            service.stopSelf();
        }
    }

    public void stopRecord(){
        recordWhileTrue = false;
    }


}