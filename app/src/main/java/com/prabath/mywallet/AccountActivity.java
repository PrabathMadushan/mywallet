package com.prabath.mywallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.prabath.mywallet.Listeners.RecordSelectListener;
import com.prabath.mywallet.adapters.RecordAdapter;

import java.util.ArrayList;
import java.util.List;

import database.local.LocalDatabaseController;
import database.local.LocalDatabaseHelper;
import database.local.models.Account;
import database.local.models.Record;

public class AccountActivity extends AppCompatActivity implements RecordSelectListener {


    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        account = (Account) getIntent().getSerializableExtra(AccountsActivity.EXTRA_ACCOUNT);
        init();
    }

    private void init() {
        setupRecycleView();
    }


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Record> records;
    private RecordAdapter recordAdapter;

    private void setupRecycleView() {
        records = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recordAdapter = new RecordAdapter(records, this);
        recyclerView.setAdapter(recordAdapter);
        showRecords();
    }


    @Override
    public void onSelect(int position, Record record) {

    }

    private void showRecords() {
        LocalDatabaseController.TableRecord tableRecord = LocalDatabaseController.getInstance(LocalDatabaseHelper.getInstance(this)).new TableRecord();
        List<Record> records = tableRecord.get(Record.FIELD_ACCOUNT + "='" + account.getId() + "'");
        this.records.addAll(records);
        recordAdapter.notifyDataSetChanged();
    }

    public static String EXTRA_RECORD = "EXTRA_RECORD";

    public void addNewRecord(View view) {
        YoYo.with(Techniques.RubberBand).duration(500).onEnd(a -> {
            Record record = new Record();
            record.setAccount(account);
            Intent intent = new Intent(this, CategorySelectorActivity.class);
            intent.putExtra(EXTRA_RECORD, record);
            startActivity(intent);
        }).playOn(view);
    }

    public void gotoAccounts(View view) {
        YoYo.with(Techniques.RubberBand).duration(500).onEnd(a -> {
            Intent intent = new Intent(this, AccountsActivity.class);
            startActivity(intent);
        }).playOn(view);

    }
}
