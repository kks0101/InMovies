package com.inmovies.inmovies.ui.error;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.inmovies.inmovies.MobileNavigationDirections;
import com.inmovies.inmovies.R;
import com.inmovies.inmovies.databinding.FragmentErrorBinding;

public class ErrorFragment extends Fragment {

    private FragmentErrorBinding fragmentErrorBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentErrorBinding = FragmentErrorBinding.inflate(inflater, container, false);
        View root = fragmentErrorBinding.getRoot();

        final Button button = fragmentErrorBinding.button;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavController navController = Navigation.findNavController(view);
                navController.popBackStack();
                navController.navigate(R.id.navigation_home);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}