package com.imooc.smartbutler.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.imooc.smartbutler.R;
import com.imooc.smartbutler.adapter.ChatListAdaper;
import com.imooc.smartbutler.entity.ChatListData;
import com.imooc.smartbutler.utils.L;
import com.imooc.smartbutler.utils.ShareUtils;
import com.imooc.smartbutler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*
* 项目名： SmartButler
* 包名： com.imooc.smartbutler.fragment
* 文件名： BulterFragment
* 创建者： HJF
* 创建时间： 2017/5/710:50
* 描述： com.imooc.smartbutler.fragment
*/

public class BulterFragment extends Fragment implements View.OnClickListener {
    private EditText ed_butler;
    private Button btn_send;
    View view;
    private List<ChatListData> mLists=new ArrayList<>();
    private  ChatListData chatListData;
    private ListView lv_butler;
    private ChatListAdaper adapter;

    SpeechSynthesizer mTts;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_butler,null);
        findView();
        return view;
    }

    private void findView() {

        //1.创建SpeechSynthesizer对象, 第二个参数：本地合成时传InitListener
        mTts = SpeechSynthesizer.createSynthesizer(getActivity(), null);
        //2.合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类
        mTts.setParameter(SpeechConstant.VOICE_NAME,"xiaoyan");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
        //设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
        //保存在SD卡需要在AndroidManifest.xml添加写SD卡权限
        //如果不需要保存合成音频，注释该行代码
        //mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");


        //初始化
        ed_butler= (EditText) view.findViewById(R.id.ed_butler);
        btn_send= (Button) view.findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);

        //设置适配器
        lv_butler= (ListView) view.findViewById(R.id.lv_butler);
        adapter=new ChatListAdaper(getActivity(),mLists);
        lv_butler.setAdapter(adapter);
        addLeftItem(getString(R.string.text_hello_tts));

    }
    private void speakVoice(String text){
        //3.开始合成
        mTts.startSpeaking(text, mSynListener);
        L.i("开始合成");

    }
    //合成监听器
     private SynthesizerListener mSynListener = new SynthesizerListener(){
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {}
        //缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {}
        //开始播放
        public void onSpeakBegin() {}
        //暂停播放
        public void onSpeakPaused() {}
        //播放进度回调
        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {}
        //恢复播放回调接口
        public void onSpeakResumed() {}

        //会话事件回调接口
        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_send:
                //1.获取输入框的内容
                String text=ed_butler.getText().toString();
                if (!TextUtils.isEmpty(text)){
                    if (text.length() > 30){
                        Toast.makeText(getActivity(), R.string.text_more_length, Toast.LENGTH_SHORT).show();
                    }else {
                        L.e(text);
                        //4.清空当前的输入框
                        ed_butler.setText("");
                        //5.添加你输入的内容到right item
                        addRightItem(text);
                        //6.发送给机器人请求返回内容
                        L.i(text);
                        String url = "http://op.juhe.cn/robot/index?info=" + text
                                + "&key=" + StaticClass.CHAT_KEY;
                        RxVolley.get(url, new HttpCallback() {
                            @Override
                            public void onSuccess(String t) {
                                //Toast.makeText(getActivity(), "Json:" + t, Toast.LENGTH_SHORT).show();
                                L.e("Json" + t);
                                parsingJson(t);
                            }
                        });
                    }

                }else {
                    Toast.makeText(getActivity(), R.string.text_tost_empty, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject=new JSONObject(t);
            JSONObject resultObject=jsonObject.getJSONObject("result");
//            JSONObject textObject=resultObject.getJSONObject("text");
            String text=resultObject.getString("text");
            addLeftItem(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //添加左边文本
    private void addLeftItem(String text) {
        boolean ischeck= ShareUtils.getBoolean(getActivity(),"ischeck",false);
        if(ischeck){
            L.i("说话啊");
            speakVoice(text);
        }

        ChatListData date = new ChatListData();
        date.setType(ChatListAdaper.VALUE_LEFT_TEXT);
        date.setText(text);
        mLists.add(date);
        //通知adapter刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        lv_butler.setSelection(lv_butler.getBottom());
    }

    //添加右边文本
    private void addRightItem(String text) {

        ChatListData date = new ChatListData();
        date.setType(ChatListAdaper.VALUE_RIGHT_TEXT);
        date.setText(text);
        mLists.add(date);
        //通知adapter刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        lv_butler.setSelection(lv_butler.getBottom());
    }
}
