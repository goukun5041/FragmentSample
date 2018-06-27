package com.websarva.wings.android.fragmentsample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
public class MenuThanksFragment extends Fragment {

    /**
     * このフラグメントが所属するアクテビティpブジェクト
     * @param savedInstanceState
     */
    private Activity _parentActivity;
    private boolean _isLayoutXLarge = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //親クラスの呼び出し
        super.onCreate(savedInstanceState);
        //所属するアクティビティオブジェクトを取得
        _parentActivity = getActivity();
        //フラグメントマネージャ取得
        FragmentManager maneger = getFragmentManager();
        //フラグメントマネージャからメニューリストフラグメント取得
        MenuListFragment menuListFragment = (MenuListFragment) maneger.findFragmentById(R.id.fragmentMenuList);
        //menuThanksFrameがnull、つまり存在しないなら
        if (menuListFragment == null) {
            //画面判定フラグを通常とする
            _isLayoutXLarge = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //フラグメントで表示する画面をXMLファイルからインフレートする
        View view = inflater.inflate(R.layout.fragment_menu_thanks, container, false);
        //bundleオブジェ宣言
        Bundle extras;
        //大画面
        if (_isLayoutXLarge) {
            extras = getArguments();
        }
        else{
            //所属アクテビティからインテント取得
            Intent intent = _parentActivity.getIntent();
            //インテントから引き継ぎデータをまとめたもの(bundleオブジェクト)を取得
            extras = intent.getExtras();
        }


        //注文した定食メイト金額変数を用意。引き継ぎデータがうまく取得できなかった時のために""で初期化
        String menuName = "";
        String menuPrice = "";
        //引き継ぎデータ(bundleオブジェクト)が存在すれば
        if(extras != null){
            //定食メイト菌がくっ取得
            menuName = extras.getString("menuName");
            menuPrice = extras.getString("menuPrice");
        }
        //定食メイト金額を表示されるtextviewを取得
        TextView tvMenuName = (TextView)view.findViewById(R.id.tvMenuName);
        TextView tvMenuPrice = (TextView)view.findViewById(R.id.tvMenuPrice);
        //textviewに名前と金額表示
        tvMenuName.setText(menuName);
        tvMenuPrice.setText(menuPrice);
        //戻るボタン取得
        Button btBackButton = (Button)view.findViewById(R.id.btBackButton);
        //戻るボタンにリスな登録
        btBackButton.setOnClickListener(new ButtonClickListener());
        //インフレートされた画面を戻り値として返す
        return view;
    }

    /**
     * ボタンが押された時の処理が記述されたメンバクラス
     */
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view){
            //大画面の時
            if (_isLayoutXLarge){
                //フラグメントマネージャ取得
                FragmentManager manager =getFragmentManager();
                //フラグメントトランザクション開始
                FragmentTransaction transaction = manager.beginTransaction();
                //自分を削除
                transaction.remove(MenuThanksFragment.this);
                transaction.commit();
            }else {
                //自分が所属するアクティ終了
                _parentActivity.finish();
            }
        }
    }
}
