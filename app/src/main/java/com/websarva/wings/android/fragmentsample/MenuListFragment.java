package com.websarva.wings.android.fragmentsample;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class MenuListFragment extends Fragment {
    /**
     * 大画面かどうかの判定フラグ
     * trueが大画面,falseが通常画面
     * 判定ロジックはどう１画面に注文完了表示用フレームレイアウトが存在するかで行う
     *
     * このフラグメントが所属するアクティビティおブジェクト
     */
    private Activity _parentActivity;
    private boolean _isLayoutXLarge = true;

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        //親クラス呼び出し
        super.onActivityCreated(savedInstanceState);
        //自分が所属するアクティビティからmenuThanksFrameを取得
        View menuThanksFrame = _parentActivity.findViewById(R.id.menuThanksFrame);
        //menuThanksFrameがnull、つまり存在しないなら
        if(menuThanksFrame == null){
            //画面判定フラグを通常とする
            _isLayoutXLarge = false;
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        //親クラスonCreate()の呼び出し
        super.onCreate(savedInstanceState);
        //所属するアクテビティオブジェクトを取得
        _parentActivity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        // フラグメントで表示する画面をXMLファイルからインフレートする
        View view = inflater.inflate(R.layout.fragment_menu_list, container, false);
        //画面部品ListViewを取得
        ListView lvmenu = (ListView)view.findViewById(R.id.lvMenu);
        //simpleadapterで使用するlistオブジェクトを用意
        List<Map<String,String>> menuList  = new ArrayList<>();
        /**
         *menuListデータ生成処理
         */
        //simpleadapter第４引数from用データ用意
        String[] from = {"name","price"};
        //simpleadapterだ５匹数to用データの用意
        int[] to = {android.R.id.text1,android.R.id.text2};
        //simpleadapterを生成
        SimpleAdapter adapter = new SimpleAdapter(_parentActivity,menuList,android.R.layout.simple_list_item_2,from,to);
        //アダプタ登録
        lvmenu.setAdapter(adapter);
        //リスな登録
        lvmenu.setOnItemClickListener(new ListItemClickListener());
        //インフレートされた画面を戻り値として返す
        return view;
    }
    /**
     * リストがタップされた時の処理が記述されたメンバクラス
     */
    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //タップされた行のデータを取得。SimpleAdapterでは一行bんのデータはMap型
            Map<String, String> item = (Map<String, String>) parent.getItemAtPosition(position);
            //定食名と金額を取得
            String menuName = item.get("name");
            String menuPrice = item.get("price");

            //引き継ぎデータをまとめてかくのうできるBundleオブジェクト生成
            Bundle bundle = new Bundle();
            //Bundleオブジェクトに引き継ぎデータ格納
            bundle.putString("menuName", "menuName");
            bundle.putString("menuPrice", "menuPrice");

            //大画面の場合
            if (_isLayoutXLarge) {
                //フラグメントマネージャー取得
                FragmentManager manager = getFragmentManager();
                //フラグメントトランザクション開始
                FragmentTransaction transaction = manager.beginTransaction();
                //注文完了フラグメント生成
                MenuThanksFragment menuThanksFragment = new MenuThanksFragment();
                //引き継ぎデータを注文完了フラグメントに格納
                menuThanksFragment.setArguments(bundle);
                //生成した注文完了フラグメントをmenuThanksFrameレイアウト部品に追加
                transaction.replace(R.id.menuThanksFrame, menuThanksFragment);
                //フラグメントトランザクションのコミット
                transaction.commit();
            }//通常画面の場合
            else {
                //インテントオブジェクトを生成
                Intent intent = new Intent(_parentActivity, MenuThanksActivity.class);
                //第２画面に送るデータを格納
                intent.putExtras(bundle);
                //第２画面の起動
                startActivity(intent);
            }


            //インテントオブジェクトを生成
            Intent intent = new Intent(_parentActivity, MenuThanksActivity.class);
            //第２画面に送るデータを格納
            intent.putExtra("menuName","menuName");
            intent.putExtra("menuPrice","menuPrice");
            //第二画面起動
            startActivity(intent);
        }
    }
}
