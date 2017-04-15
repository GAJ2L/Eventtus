package com.gaj2l.eventtus.activities;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaj2l.eventtus.R;

public class ContactFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ((BaseActivity)getContext()).setTitle(R.string.title_contact);

        View view = inflater.inflate(R.layout.fragment_contact, container, false );

        return view;
    }
}
