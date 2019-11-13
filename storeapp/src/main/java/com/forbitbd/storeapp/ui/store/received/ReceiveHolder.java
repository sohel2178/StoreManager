package com.forbitbd.storeapp.ui.store.received;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.baseAdapter.BaseHolder;
import com.forbitbd.storeapp.models.Receive;
import com.squareup.picasso.Picasso;

public class ReceiveHolder extends BaseHolder<Receive,ReceiveListener> implements View.OnClickListener {

    ImageView ivImage;
    TextView tvName,tvSupplier;

    ImageView ivEdit,ivDelete;

    public ReceiveHolder(View itemView, ReceiveListener listener) {
        super(itemView, listener);
        ivImage = itemView.findViewById(R.id.image);
        ivEdit = itemView.findViewById(R.id.edit);
        ivDelete = itemView.findViewById(R.id.delete);

        tvName = itemView.findViewById(R.id.name);
        tvSupplier = itemView.findViewById(R.id.supplier);


        ivImage.setOnClickListener(this);
        ivEdit.setOnClickListener(this);
        ivDelete.setOnClickListener(this);

    }

    @Override
    public void bind(Receive receive) {

        tvName.setText(receive.getName()
                .concat(" ")
                .concat(String.valueOf(receive.getQuantity()))
                .concat(" ").concat(receive.getUnit())
        );

        tvSupplier.setText("Supplier: ".concat(receive.getReceived_from().getName()));

        Picasso.with(itemView.getContext())
                .load(receive.getImage())
                .into(ivImage);

    }

    @Override
    public void onClick(View view) {
        if(view==ivImage){
            getListener().onImageClick(getAdapterPosition());
        }
    }
}
