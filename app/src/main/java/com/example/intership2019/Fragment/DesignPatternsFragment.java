package com.example.intership2019.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.intership2019.Constant;
import com.example.intership2019.Fragment.DPAbstractFactory.Computer;
import com.example.intership2019.Fragment.DPAbstractFactory.ComputerFactory;
import com.example.intership2019.Fragment.DPAbstractFactory.PCFactory;
import com.example.intership2019.Fragment.DPAbstractFactory.ServerFactory;
import com.example.intership2019.Fragment.DPBuilder.User;
import com.example.intership2019.Fragment.DPDependencyInjection.MyComponent;
import com.example.intership2019.Fragment.DPFactoryMethod.Phone;
import com.example.intership2019.Fragment.DPFactoryMethod.PhoneFactory;
import com.example.intership2019.Fragment.DPFactoryMethod.PhoneType;
import com.example.intership2019.Fragment.DPObserver.Observer;
import com.example.intership2019.Fragment.DPObserver.UserDataRepository;
import com.example.intership2019.Fragment.DPPrototype.WinOS;
import com.example.intership2019.R;

import java.util.Arrays;

import javax.inject.Inject;

import androidx.fragment.app.Fragment;


public class DesignPatternsFragment extends Fragment implements Observer {


    private TextView mTextAbstractFactory;
    private TextView mTextBuilder;
    private TextView mTextFactoryMethod;
    private TextView mTextPrototype;
    private TextView mTextObserver;
    private UserDataRepository mUserDataRepository;

    private MyComponent myComponent;
    @Inject
    SharedPreferences sharedPreferences;
    private EditText mInUsername;
    private EditText mInNumber;
    private Button mBtnSave;
    private Button mBtnGet;

    public DesignPatternsFragment() {
        // Required empty public constructor

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserDataRepository = UserDataRepository.getInstance();
        mUserDataRepository.addObserver(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_design_patterns, container, false);
        init(view);
        Computer pc = ComputerFactory.createComputer(new PCFactory("2 GB", "500 GB", "2.4 GHz"));
        Computer server = ComputerFactory.createComputer(new ServerFactory("16 GB", "1 TB", "2.9 GHz"));
        Log.d("pc and server", "" + pc + server);
        mTextAbstractFactory.setText(pc + "\n" + server);

        User user = new User.Builder().name("design pattern builder").age(21).languages(Arrays.asList("Vietnam", "English")).build();
        mTextBuilder.setText(user.toString());

        Phone createPhone = PhoneFactory.creatPhone(PhoneType.SAMSUNG, "beautiful", "snapdragon 625");
        Log.d(Constant.TAG, "" + createPhone);
        mTextFactoryMethod.setText(createPhone.descriptionShape() + "\n" + createPhone.descriptionParam());

        WinOS winOS1 = new WinOS("Win 10", "office 2019", "BKAV", "Chrome", "");
        WinOS winOS2 = winOS1.clone();
        winOS2.setOthers("Android Studio");
        mTextPrototype.setText(winOS1 + "\n" + winOS2);
        onChange("vu tien dat \n", 22);

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", mInUsername.getText().toString().trim());
                editor.putString("number", mInNumber.getText().toString().trim());
                editor.apply();
            }
        });
        mBtnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInUsername.setText(sharedPreferences.getString("username", "default"));
                mInNumber.setText(sharedPreferences.getString("number", "12345"));
            }
        });
        return view;
    }

    public void init(View view) {
        mTextAbstractFactory = view.findViewById(R.id.textAbstractFactory);
        mTextBuilder = view.findViewById(R.id.textBuilder);
        mTextFactoryMethod = view.findViewById(R.id.textFactoryMethod);
        mTextPrototype = view.findViewById(R.id.textPrototype);
        mTextObserver = view.findViewById(R.id.textObserver);
        mInUsername = view.findViewById(R.id.inUsername);
        mInNumber = view.findViewById(R.id.inNumber);
        mBtnSave = view.findViewById(R.id.btnSave);
        mBtnGet = view.findViewById(R.id.btnGet);
    }


    @Override
    public void onChange(String fullname, int age) {
        mTextObserver.setText(fullname + age);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUserDataRepository != null) {
            mUserDataRepository.removeObserver(this);
        }
    }
}
