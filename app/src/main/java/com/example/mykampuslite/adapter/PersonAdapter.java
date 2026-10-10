package com.example.mykampuslite.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mykampuslite.R;
import com.example.mykampuslite.model.Dosen;
import com.example.mykampuslite.model.EvaluasiAkademik;
import com.example.mykampuslite.model.Mahasiswa;
import com.example.mykampuslite.model.Person;

public class PersonAdapter extends ListAdapter<Person, PersonAdapter.PersonViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Person person);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(Person person);
    }

    private final OnItemClickListener clickListener;
    private final OnItemLongClickListener longClickListener;

    public PersonAdapter(OnItemClickListener clickListener, OnItemLongClickListener longClickListener) {
        super(DIFF_CALLBACK);
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
    }

    private static final DiffUtil.ItemCallback<Person> DIFF_CALLBACK = new DiffUtil.ItemCallback<Person>() {
        @Override
        public boolean areItemsTheSame(@NonNull Person oldItem, @NonNull Person newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Person oldItem, @NonNull Person newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        Person person = getItem(position);
        holder.bind(person, clickListener, longClickListener);
    }

    static class PersonViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvNama, tvDetail, tvRole, tvStatus;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvDetail = itemView.findViewById(R.id.tvDetail);
            tvRole = itemView.findViewById(R.id.tvRole);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }

        public void bind(Person person, OnItemClickListener clickListener, OnItemLongClickListener longClickListener) {
            if (tvNama != null) tvNama.setText(person.getNama());
            if (tvRole != null) tvRole.setText(person.getRole());

            if (tvStatus != null && person instanceof EvaluasiAkademik) {
                tvStatus.setText("Status: " + ((EvaluasiAkademik) person).getStatusAkademik());
            }

            if (tvDetail != null) {
                if (person instanceof Mahasiswa) {
                    Mahasiswa m = (Mahasiswa) person;
                    tvDetail.setText("NIM: " + m.getNim() + " | IPK: " + m.getIpk());
                } else if (person instanceof Dosen) {
                    Dosen d = (Dosen) person;
                    tvDetail.setText("NIDN: " + d.getNidn() + " | MK: " + d.getMataKuliahDiampu().size());
                }
            }

            itemView.setOnClickListener(v -> {
                if (clickListener != null) clickListener.onItemClick(person);
            });

            itemView.setOnLongClickListener(v -> {
                if (longClickListener != null) longClickListener.onItemLongClick(person);
                return true;
            });
        }
    }
}