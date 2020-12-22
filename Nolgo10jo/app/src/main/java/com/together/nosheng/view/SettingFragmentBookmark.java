package com.together.nosheng.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.together.nosheng.R;
import com.together.nosheng.adapter.BookmarkAdapter;
import com.together.nosheng.databinding.SettingFragmentBookmarkBinding;
import com.together.nosheng.model.plan.Plan;
import com.together.nosheng.viewmodel.UserViewModel;

import java.util.List;


public class SettingFragmentBookmark extends Fragment {
    private SettingFragmentBookmarkBinding binding;
    private RecyclerView mRecyclerView;
    private UserViewModel userViewModel;
    private BookmarkAdapter bookmarkAdapter;
    private BookmarkAdapter.BookmarkRecyclerViewClickListener listener;

    public static SettingFragmentBookmark newInstance() {
        return new SettingFragmentBookmark();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.setting_fragment_bookmark,container,false);
        View view = binding.getRoot();
        Context context = view.getContext();

        mRecyclerView = view.findViewById(R.id.setting_listView_bookmark);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        userViewModel= new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        userViewModel.setBookmarkList();
        userViewModel.getBookmarkList().observe(getViewLifecycleOwner(), new Observer<List<Plan>>() {
            @Override
            public void onChanged(List<Plan> plans) {
                if (plans ==null){
                    Toast.makeText(context, "북마크 없어!", Toast.LENGTH_LONG).show();
                }else{
                    bookmarkAdapter = new BookmarkAdapter(plans, listener, context );
                    binding.settingListViewBookmark.setAdapter(bookmarkAdapter);
                }
            }
        });

        return view;

    }
    private void setOnClickListener () {
        listener = new BookmarkAdapter.BookmarkRecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {

            }
        };
    }

}
