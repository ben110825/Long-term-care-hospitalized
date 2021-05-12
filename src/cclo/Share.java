/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cclo;

/**
 *
 * @author panda
 */
public interface Share {

    double DIST_THRESH = 3.0;
    double PCUT = 0.5;
    double MBOUND = 3.0;
    double MAT_RAT = 0.08;
    double DEC_RAT = 0.995;
    double MAT_L = 0.6;
    double REW = 0.1;
    double ADJ_R = 0.05;
    int TRAIN_LEN = 8;
    
    
    static final String options[] = {"Bell", "Pot", "Phone", "Door", "Nomal"};

    enum STATE {

        START, ENDING, MATCH, NORMAL, RECORDING, STOP, CLOSE, OPEN, READY, EMPTY, TRAINING
    }

    int FFT_STEP = 128;
    int ADD = 1;
    int REMOVER = 2;
    int CHAT = 3;
    int OLIGHT = 5;
    int CLIGHT = 6;
    int OAIRCONDITION = 7;
    int CAIRCONDITION = 8;
    int ASKLIGHT = 9;
    int ASKAIRCONDITION = 10;
    int ASKMUSIC = 11;
    int MUSICNAME = 12;
    int MUSICNAME_S = 13;
    int MUSICPLAY = 14;
    int TEMPERATURE = 15;
    int HYGRON = 16;
    int ASKHISTORY = 17;
    int HISTORY = 18;
    int VOICESTOP = 19;
    int MUSICSTOP = 20;
    final int NONE = 0;
    final int ASK_TYPE = 1;
    final int REP_TYPE = 2;
    final int OPEN_TFILE = 3;
    final int RECORD = 4;
    final int CLEAR_TFILE = 5;
    final int STOP_RECORD = 6;
    final int SAVE_TFILE = 7;
    final int NORMAL = 8;
    final int POT = 9;
    final int WATER = 10;
    final int BELL = 11;
    final int DOOR = 12;
    final int LOAD_TRAINER = 13;
    final int TRAIN = 14;
    final int MATCH = 15;
    final int LOAD_SOM = 16;
    final int SAVE_SOM = 17;
    final int TYPE_POT = 18;
    final int TYPE_WATER = 19;
    final int TYPE_BELL = 20;
    final int TYPE_DOOR = 21;
    final int statistics[] = new int[options.length];
}
