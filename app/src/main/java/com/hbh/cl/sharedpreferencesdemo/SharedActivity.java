package com.hbh.cl.sharedpreferencesdemo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

/**
 * Created by hbh on 2017/5/12.
 */

public class SharedActivity extends AppCompatActivity implements View.OnClickListener{

    EditText name,age,sex;
    Button save,delete;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared);
        name = (EditText) findViewById(R.id.name);
        age = (EditText) findViewById(R.id.age);
        sex = (EditText) findViewById(R.id.sex);
        save = (Button) findViewById(R.id.save);
        delete = (Button) findViewById(R.id.delete);
        preferences = getSharedPreferences("TestCache", MODE_PRIVATE);
        editor = preferences.edit();
        save.setOnClickListener(this);
        delete.setOnClickListener(this);
        initData();
    }

    public void initData(){
        name.setText(preferences.getString("name",""));
        age.setText(preferences.getString("age",""));
        sex.setText(preferences.getString("sex",""));
    }

    /**
     * 保存SharedPreferences数据
     */
    public void saveCache(){
        editor.putString("name",name.getText().toString());
        editor.putString("age",age.getText().toString());
        editor.putString("sex",sex.getText().toString());
        if(editor.commit()){
            Toast.makeText(this,"保存成功！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 删除SharedPreferences数据文件
     * @param files
     */
    public void deleteCache(File[] files){

        //清空数据
        editor.clear();
        editor.commit();

        boolean flag;
        for(File itemFile : files){
            flag = itemFile.delete();
            if (flag == false) {
                deleteCache(itemFile.listFiles());
            }
        }
        Toast.makeText(this, "清除缓存成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.save:
                saveCache();
                break;
            case R.id.delete:
                File[] files = new File("/data/data/"+this.getPackageName()+"/shared_prefs").listFiles();
                deleteCache(files);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //删除缓存数据的xml文件之后再次执行commit()，删除的文件会重生。
        editor.putString("address","济南");
        editor.commit();
        this.finish();
    }
}
