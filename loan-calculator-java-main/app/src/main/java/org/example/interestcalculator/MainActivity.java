package org.example.interestcalculator;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.example.interestcalculator.adapter.PaymentAdapter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etAmount, etTermY, etTermM, etInterest, etDeferralMonths, etDeferralInterest;
    private RadioButton rbAnnuity, rbLinear;
    private TextView tvResultLabel;
    private Button btnStartDate, btnFilterStart, btnFilterEnd, btnDeferralStart, btnCalculate, btnDownload;
    private RecyclerView recyclerView;
    private PaymentAdapter adapter;

    private LocalDate startDate, filterStart, filterEnd, deferralStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etAmount = findViewById(R.id.amount);
        etTermY = findViewById(R.id.termY);
        etTermM = findViewById(R.id.termM);
        etInterest = findViewById(R.id.interest);
        etDeferralMonths = findViewById(R.id.deferralMonths);
        etDeferralInterest = findViewById(R.id.deferralInterest);
        rbAnnuity = findViewById(R.id.annuity);
        rbLinear = findViewById(R.id.linear);
        tvResultLabel = findViewById(R.id.resultLabel);
        btnStartDate = findViewById(R.id.startDateButton);
        btnFilterStart = findViewById(R.id.filterStartButton);
        btnFilterEnd = findViewById(R.id.filterEndButton);
        btnDeferralStart = findViewById(R.id.deferralStartButton);
        btnCalculate = findViewById(R.id.calculate);
        btnDownload = findViewById(R.id.download);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PaymentAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        btnStartDate.setOnClickListener(v -> showDatePicker(date -> {
            startDate = date;
            btnStartDate.setText("Start: " + date.toString());
        }));

        btnFilterStart.setOnClickListener(v -> showDatePicker(date -> {
            filterStart = date;
            btnFilterStart.setText("F.Start: " + date.toString());
        }));

        btnFilterEnd.setOnClickListener(v -> showDatePicker(date -> {
            filterEnd = date;
            btnFilterEnd.setText("F.End: " + date.toString());
        }));

        btnDeferralStart.setOnClickListener(v -> showDatePicker(date -> {
            deferralStart = date;
            btnDeferralStart.setText("D.Start: " + date.toString());
        }));

        btnCalculate.setOnClickListener(v -> pressedCalculate());
        btnDownload.setOnClickListener(v -> {
            List<Payment> payments = adapter.getPayments();
            if (payments == null || payments.isEmpty()) {
                Toast.makeText(this, "Calculate first to download", Toast.LENGTH_SHORT).show();
                return;
            }
            shareCsv(payments);
        });
    }

    private void shareCsv(List<Payment> payments) {
        StringBuilder csv = new StringBuilder();
        csv.append("Date,Payment,Principal,Interest,Remaining\n");
        for (Payment p : payments) {
            csv.append(p.getDate()).append(",")
                    .append(p.getMonthlyPayment()).append(",")
                    .append(p.getPrincipalPart()).append(",")
                    .append(p.getInterestPart()).append(",")
                    .append(p.getLeft()).append("\n");
        }

        try {
            File cachePath = new File(getCacheDir(), "reports");
            cachePath.mkdirs();
            File csvFile = new File(cachePath, "loan_report.csv");
            FileOutputStream stream = new FileOutputStream(csvFile);
            stream.write(csv.toString().getBytes());
            stream.close();

            Uri contentUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", csvFile);

            if (contentUri != null) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                shareIntent.setDataAndType(contentUri, getContentResolver().getType(contentUri));
                shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                shareIntent.setType("text/csv");
                startActivity(Intent.createChooser(shareIntent, "Save or share report"));
            }
        } catch (IOException e) {
            Toast.makeText(this, "Error generating CSV", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDatePicker(OnDateSelectedListener listener) {
        LocalDate now = LocalDate.now();
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            listener.onDateSelected(LocalDate.of(year, month + 1, dayOfMonth));
        }, now.getYear(), now.getMonthValue() - 1, now.getDayOfMonth()).show();
    }

    interface OnDateSelectedListener {
        void onDateSelected(LocalDate date);
    }

    private void pressedCalculate() {
        try {
            String amtStr = etAmount.getText().toString();
            if (amtStr.isEmpty()) return;
            double amt = Double.parseDouble(amtStr);
            if (amt <= 0) {
                tvResultLabel.setText("Invalid input");
                return;
            }

            String termMStr = etTermM.getText().toString();
            int month = termMStr.isEmpty() ? 0 : Integer.parseInt(termMStr);
            if (month >= 12 || month < 0) {
                tvResultLabel.setText("Invalid input");
                return;
            }

            String termYStr = etTermY.getText().toString();
            int year = termYStr.isEmpty() ? 0 : Integer.parseInt(termYStr);
            if (year > 999 || year < 0) {
                tvResultLabel.setText("Invalid input");
                return;
            }

            int term = year * 12 + month;
            if (term <= 0) {
                tvResultLabel.setText("Loan term must be at least one month");
                return;
            }

            String interestStr = etInterest.getText().toString();
            if (interestStr.isEmpty()) return;
            double intrst = Double.parseDouble(interestStr) / 1200;
            if (intrst > 999 || intrst < 0) {
                tvResultLabel.setText("Invalid input");
                return;
            }

            boolean isannuity = rbAnnuity.isChecked();
            boolean islinear = rbLinear.isChecked();

            LocalDate start = startDate != null ? startDate : LocalDate.now();
            LocalDate fstart = filterStart != null ? filterStart : LocalDate.MIN;
            LocalDate fend = filterEnd != null ? filterEnd : LocalDate.MAX;

            if (fstart.isAfter(fend)) {
                tvResultLabel.setText("Filter start must be before filter end");
                return;
            }

            int dMonths = 0;
            double dInterest = 0;
            LocalDate dStart = deferralStart;

            String dMonthsStr = etDeferralMonths.getText().toString();
            String dInterestStr = etDeferralInterest.getText().toString();

            if (!dMonthsStr.isEmpty() || !dInterestStr.isEmpty() || dStart != null) {
                if (dMonthsStr.isEmpty() || dInterestStr.isEmpty() || dStart == null) {
                    tvResultLabel.setText("Enter deferral date, duration, and interest");
                    return;
                }
                dMonths = Integer.parseInt(dMonthsStr);
                dInterest = Double.parseDouble(dInterestStr) / 1200;
                if (dMonths <= 0 || dInterest < 0 || dStart.isBefore(start) || !dStart.isBefore(start.plusMonths(term))) {
                    tvResultLabel.setText("Invalid deferral settings");
                    return;
                }
            }

            CalcParams calcParams = new CalcParams(amt, dMonths, term, intrst, dInterest, start, fstart, fend, dStart);
            PaymentCalculator calculator;
            if (isannuity) {
                calculator = new AnnuityCalculator(calcParams);
            } else if (islinear) {
                calculator = new LinearCalculator(calcParams);
            } else {
                tvResultLabel.setText("Pick a payment method");
                return;
            }

            Result res = calculator.calculate();
            adapter.setPayments(res.getList());
            tvResultLabel.setText(res.getMessage());

        } catch (NumberFormatException e) {
            tvResultLabel.setText("Invalid input");
        }
    }
}
