package com.example.administrator.myapplication.fragment;


import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract  class BaseInputCellFragment extends Fragment {
 public abstract void setHintText(String hint);
    public abstract void setLabelText(String  labelText);

}
