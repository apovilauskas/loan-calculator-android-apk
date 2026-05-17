package org.example.interestcalculator.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.example.interestcalculator.Payment;
import org.example.interestcalculator.R;
import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {

    private List<Payment> payments;

    public PaymentAdapter(List<Payment> payments) {
        this.payments = payments;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Payment payment = payments.get(position);
        holder.tvDate.setText(payment.getDate());
        holder.tvPayment.setText(String.valueOf(payment.getMonthlyPayment()));
        holder.tvPrincipal.setText(String.valueOf(payment.getPrincipalPart()));
        holder.tvInterest.setText(String.valueOf(payment.getInterestPart()));
        holder.tvRemaining.setText(String.valueOf(payment.getLeft()));
    }

    @Override
    public int getItemCount() {
        return payments != null ? payments.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvPayment, tvPrincipal, tvInterest, tvRemaining;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvPayment = itemView.findViewById(R.id.tvPayment);
            tvPrincipal = itemView.findViewById(R.id.tvPrincipal);
            tvInterest = itemView.findViewById(R.id.tvInterest);
            tvRemaining = itemView.findViewById(R.id.tvRemaining);
        }
    }
}
