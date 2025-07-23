package com.synapse.social.studioasinc.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import androidx.annotation.RawRes

class SoundEffectPlayer(private val context: Context) {  // Code by Ashik from StudioAsInc.

    private val soundPool: SoundPool
    private val soundMap: MutableMap<Int, Int> = mutableMapOf()

    init {
        soundPool = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()

            SoundPool.Builder()
                .setMaxStreams(5)
                .setAudioAttributes(audioAttributes)
                .build()
        } else {
            SoundPool(5, android.media.AudioManager.STREAM_MUSIC, 0)
        }
    }

    fun load(@RawRes resId: Int) {
        if (!soundMap.containsKey(resId)) {
            val soundId = soundPool.load(context, resId, 1)
            soundMap[resId] = soundId
        }
    }

    fun play(@RawRes resId: Int) {
        val soundId = soundMap[resId]
        if (soundId != null) {
            soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
        } else {
            val newSoundId = soundPool.load(context, resId, 1)
            soundMap[resId] = newSoundId
            soundPool.setOnLoadCompleteListener { _, sid, _ ->
                if (sid == newSoundId) {
                    soundPool.play(sid, 1f, 1f, 0, 0, 1f)
                }
            }
        }
    }

    fun release() {
        soundPool.release()
        soundMap.clear()
    }
}