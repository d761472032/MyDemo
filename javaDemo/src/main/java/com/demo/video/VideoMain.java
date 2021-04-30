package com.demo.video;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;

import java.io.File;
import java.time.Instant;

public class VideoMain {

    public static void main(String[] args) {
        String filepath = "E:\\星空下\\文件\\source.MP4";
        String targetpath = "E:\\星空下\\文件\\" + Instant.now().toEpochMilli() + ".mp4";
        ffmpegFrameGrabberConvertToMp4(new File(filepath), targetpath);

//        ffmpegFrameGrabberConvertToMp4(new File(args[0]), args[1]);
    }

    public static void ffmpegFrameGrabberConvertToMp4(File file, String targetVideoPath) {
        FFmpegFrameRecorder recorder = null;
        try (FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(file);) {
            frameGrabber.start();
            recorder = new FFmpegFrameRecorder(targetVideoPath, frameGrabber.getImageWidth(), frameGrabber.getImageHeight(), frameGrabber.getAudioChannels());
            // 原始视频格式
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); //avcodec.AV_CODEC_ID_H264  //AV_CODEC_ID_MPEG4
            // 期望视频格式
            recorder.setFormat("mp4");// 此处设置不同格式，可转不同格式数据
            recorder.setFrameRate(frameGrabber.getFrameRate());
            //recorder.setSampleFormat(frameGrabber.getSampleFormat()); //
            recorder.setSampleRate(frameGrabber.getSampleRate());
            recorder.setAudioChannels(frameGrabber.getAudioChannels());
            recorder.setFrameRate(frameGrabber.getFrameRate());
            recorder.start();

            Frame captured_frame = null;
            while ((captured_frame = frameGrabber.grabFrame()) != null) {
                try {
                    recorder.setTimestamp(frameGrabber.getTimestamp());
                    recorder.record(captured_frame);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            recorder.stop();
            recorder.release();
            frameGrabber.stop();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (recorder != null) {
                try {
                    recorder.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

}
