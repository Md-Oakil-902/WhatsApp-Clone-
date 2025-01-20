package com.oakil.whatsapp.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.oakil.whatsapp.Fragments.CallsFragment;
import com.oakil.whatsapp.Fragments.ChatsFragment;
import com.oakil.whatsapp.Fragments.StatusFragments;

import java.util.ArrayList;
import java.util.List;

public class FragmentsAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragmentList = new ArrayList<>();

    public FragmentsAdapter(@NonNull FragmentManager fm) {
        super(fm);
        fragmentList.add(new ChatsFragment());
        fragmentList.add(new StatusFragments());
        fragmentList.add(new CallsFragment());
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ChatsFragment();
            case 1:
                return new StatusFragments();
            case 2:
                return new CallsFragment();
            default:
                return new ChatsFragment();
        }
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        String title = null;
        if (position == 0) {
            title = "Chats";
        }
        if (position == 1) {
            title = "Status";
        }
        if (position == 2) {
            title = "Calls";
        }
        return title;

    }
}
