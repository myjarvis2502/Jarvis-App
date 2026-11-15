package com.jarvis.app;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity {

    TextToSpeech tts;
    TextView out;
    Button langBtn;
    boolean hindi = true;
    static final int REQ = 1;

    static final String[] FILLER = {
        "open","ओपन","खोलो","खोल","kholo",
        "youtube","यूट्यूब","यू","ट्यूब",
        "google","गूगल",
        "search","सर्च","खोजो","खोज","ढूंढो","ढूँढो",
        "karo","करो","कर","kar","do","दो",
        "on","पर","par","and","और","aur","for","me","में","mein"
    };

    static final String[][] ALIAS = {
        {"इंस्टाग्राम","instagram"},
        {"व्हाट्सएप","whatsapp"},{"व्हाट्सऐप","whatsapp"},{"वॉट्सऐप","whatsapp"},{"वाट्सएप","whatsapp"},
        {"फेसबुक","facebook"},{"क्रोम","chrome"},{"कैमरा","camera"},{"गैलरी","gallery"},
        {"सेटिंग्स","settings"},{"सेटिंग","settings"},{"कैलकुलेटर","calculator"},
        {"स्नैपचैट","snapchat"},{"टेलीग्राम","telegram"},{"जीमेल","gmail"},
        {"मैप्स","maps"},{"फोन","phone"},{"घड़ी","clock"},{"प्ले स्टोर","play store"},
        {"यूट्यूब","youtube"},{"गूगल","google"},{"स्पॉटिफाई","spotify"}
    };

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER);
        root.setBackgroundColor(Color.parseColor("#080e1c"));
        root.setPadding(40, 40, 40, 40);

        TextView title = new TextView(this);
        title.setText("MAX AI");
        title.setTextSize(36);
        title.setTextColor(Color.parseColor("#00e5ff"));
        title.setGravity(Gravity.CENTER);

        Button talk = new Button(this);
        talk.setText("TALK TO MAX");
        talk.setOnClickListener(v -> listen());

        langBtn = new Button(this);
        langBtn.setText("Language: हिंदी");
        langBtn.setOnClickListener(v -> toggleLang());

        out = new TextView(this);
        out.setText("नमस्ते! मैं MAX हूँ। बटन दबाकर बोलिए।");
        out.setTextColor(Color.WHITE);
        out.setTextSize(18);
        out.setGravity(Gravity.CENTER);
        out.setPadding(20, 50, 20, 20);

        root.addView(title);
        root.addView(talk);
        root.addView(langBtn);
        root.addView(out);
        setContentView(root);

        tts = new TextToSpeech(this, status -> setTtsLang());
    }

    void setTtsLang() {
        if (tts != null) {
            tts.setLanguage(new Locale(hindi ? "hi" : "en", "IN"));
        }
    }

    String t(String hi, String en) {
        return hindi ? hi : en;
    }

    void speak(String text) {
        out.setText(text);
        if (tts != null) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "jarvis");
        }
    }

    void toggleLang() {
        hindi = !hindi;
        langBtn.setText(hindi ? "Language: हिंदी" : "Language: English");
        setTtsLang();
        speak(t("अब मैं हिंदी में बात करूँगा।", "Now I will talk in English."));
    }

    void listen() {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(Recon
