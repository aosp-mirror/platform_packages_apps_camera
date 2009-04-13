/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.camera;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;

/**
 *  CameraSettings
 */
public class CameraSettings extends PreferenceActivity
    implements OnSharedPreferenceChangeListener
{
    public static final String KEY_VIDEO_QUALITY = "pref_camera_videoquality_key";
    public static final boolean DEFAULT_VIDEO_QUALITY_VALUE = true;

    public static final String KEY_AUDIO_ENCODER = "pref_camera_audioencoder_key";
    public static final int DEFAULT_AUDIO_ENCODER_VALUE = 1;

    public static final String KEY_VIDEO_ENCODER = "pref_camera_videoencoder_key";
    public static final int DEFAULT_VIDEO_ENCODER_VALUE = 1;

    public static final String KEY_OUTPUT_FORMAT = "pref_camera_outputformat_key";
    public static final int DEFAULT_OUTPUT_FORMAT_VALUE = 1;


    private ListPreference mVideoQuality;
    private ListPreference mAudioEncoder;
    private ListPreference mVideoEncoder;
    private ListPreference mOutputFormat;

    public CameraSettings()
    {
    }

    /** Called with the activity is first created. */
    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.camera_preferences);

        initUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateVideoQuality();
        updateAudioEncoder();
        updateVideoEncoder();
        updateOutputFormat();
    }

    private void initUI() {
        mVideoQuality = (ListPreference) findPreference(KEY_VIDEO_QUALITY);
        mAudioEncoder = (ListPreference) findPreference(KEY_AUDIO_ENCODER);
        mVideoEncoder = (ListPreference) findPreference(KEY_VIDEO_ENCODER);
        mOutputFormat = (ListPreference) findPreference(KEY_OUTPUT_FORMAT);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    private void updateVideoQuality() {
        boolean vidQualityValue = getBooleanPreference(mVideoQuality, DEFAULT_VIDEO_QUALITY_VALUE);
        int vidQualityIndex = vidQualityValue ? 1 : 0;
        String[] vidQualities =
            getResources().getStringArray(R.array.pref_camera_videoquality_entries);
        String vidQuality = vidQualities[vidQualityIndex];
        mVideoQuality.setSummary(vidQuality);
    }

    private void updateAudioEncoder() {
        int vidAudioEncoderValue = getIntPreference(mAudioEncoder,DEFAULT_AUDIO_ENCODER_VALUE );
        int vidAudioEncoderIndex = 0;
        if(vidAudioEncoderValue == 1){        // MediaRecorder.AudioEncoder.AMR_NB
            vidAudioEncoderIndex = 0;
        }
        else if(vidAudioEncoderValue == 2){   // MediaRecorder.AudioEncoder.AAC

            vidAudioEncoderIndex = 1;
        }
        String[] vidAudioEncoders =
            getResources().getStringArray(R.array.pref_camera_audioencoder_entries);
        String vidAudioEncoder = vidAudioEncoders[vidAudioEncoderIndex];
        mAudioEncoder.setSummary(vidAudioEncoder);
    }

    private void updateVideoEncoder() {
        int vidVideoEncoderValue = getIntPreference(mVideoEncoder,DEFAULT_VIDEO_ENCODER_VALUE );
        int vidVideoEncoderIndex = 0;
        if(vidVideoEncoderValue == 1){        // MediaRecorder.VideoEncoder.H263
            vidVideoEncoderIndex = 0;
        }
        else if(vidVideoEncoderValue == 2){   // MediaRecorder.VideoEncoder.H264

            vidVideoEncoderIndex = 1;
        }
        else if(vidVideoEncoderValue == 3){   // MediaRecorder.VideoEncoder.MPEG4

            vidVideoEncoderIndex = 2;
        }
        String[] vidVideoEncoders =
            getResources().getStringArray(R.array.pref_camera_videoencoder_entries);
        String vidVideoEncoder = vidVideoEncoders[vidVideoEncoderIndex];
        mVideoEncoder.setSummary(vidVideoEncoder);
    }

    private void updateOutputFormat() {
        int vidOutputFormatValue = getIntPreference(mOutputFormat,DEFAULT_OUTPUT_FORMAT_VALUE );
        int vidOutputFormatIndex = 0;
        if(vidOutputFormatValue == 1){        // MediaRecorder.OutputFormat.THREE_GPP
            vidOutputFormatIndex = 0;
        }
        else if(vidOutputFormatValue == 2){   // MediaRecorder.OutputFormat.MPEG_4

            vidOutputFormatIndex = 1;
        }
        else if(vidOutputFormatValue == 3){   // MediaRecorder.OutputFormat.RAW_AMR

            vidOutputFormatIndex = 2;
        }
        String[] vidOutputFormats =
            getResources().getStringArray(R.array.pref_camera_outputformat_entries);
        String vidOutputFormat = vidOutputFormats[vidOutputFormatIndex];
        mOutputFormat.setSummary(vidOutputFormat);
    }

    private static int getIntPreference(ListPreference preference, int defaultValue) {
        String s = preference.getValue();
        int result = defaultValue;
        try {
            result = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            // Ignore, result is already the default value.
        }
        return result;
    }

    private boolean getBooleanPreference(ListPreference preference, boolean defaultValue) {
        return getIntPreference(preference, defaultValue ? 1 : 0) != 0;
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
            String key) {
           if (key.equals(KEY_VIDEO_QUALITY)) {
               updateVideoQuality();
           }else if (key.equals(KEY_AUDIO_ENCODER)) {
               updateAudioEncoder();
           }else if (key.equals(KEY_VIDEO_ENCODER)) {
               updateVideoEncoder();
           }else if (key.equals(KEY_OUTPUT_FORMAT)) {
               updateOutputFormat();
           }
    }
}

