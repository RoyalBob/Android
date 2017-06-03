package com.royalbob.realwordandbigadventure;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TextRead {

    private Context context;
    private int judgement;
    //构造函数
    public TextRead(Context context,int judgement) {
        this.context = context;
        this.judgement = judgement;
    }

    private String readLineNumber(int lineNumber) throws IOException {
        String line;
        String s = null;
        BufferedReader br = null;
        int i = -1;
        if (judgement == 1){
            InputStream stream = context.getAssets().open("tips_realword.txt");
            br = new BufferedReader(new InputStreamReader(stream));
        }else{
            InputStream stream = context.getAssets().open("tips_bigadventure.txt");
            br = new BufferedReader(new InputStreamReader(stream));
        }
            while ((line = br.readLine()) != null) {
                i++;
                if(lineNumber == i){
                    s = line;
                }
        }
        br.close();
        return s;
    }

    public String LineRead ()throws IOException {
        String s = null;
        BufferedReader br = null;
        int i = -1;
        try {
            if (judgement == 1){
                InputStream stream = context.getAssets().open("tips_realword.txt");
                br = new BufferedReader(new InputStreamReader(stream));
            }else {
                InputStream stream = context.getAssets().open("tips_bigadventure.txt");
                br = new BufferedReader(new InputStreamReader(stream));
            }
                String line = null;
                while ((line = br.readLine()) != null) {
                    i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        int randomnum = (int) (Math.random()*i);
        s = readLineNumber(randomnum);
        return s;
    }
}
