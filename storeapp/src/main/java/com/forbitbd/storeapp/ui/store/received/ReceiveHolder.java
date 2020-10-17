package com.forbitbd.storeapp.ui.store.received;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.baseAdapter.BaseHolder;
import com.forbitbd.storeapp.models.Receive;

public class ReceiveHolder extends BaseHolder<Receive,ReceiveListener> implements View.OnClickListener {

    TextView tvName,tvSupplier;

    ImageView ivEdit,ivDelete,ivAttach;

    public ReceiveHolder(View itemView, ReceiveListener listener) {
        super(itemView, listener);
        ivAttach = itemView.findViewById(R.id.attach);
        ivEdit = itemView.findViewById(R.id.edit);
        ivDelete = itemView.findViewById(R.id.delete);

        tvName = itemView.findViewById(R.id.name);
        tvSupplier = itemView.findViewById(R.id.supplier);


        ivAttach.setOnClickListener(this);
        ivEdit.setOnClickListener(this);
        ivDelete.setOnClickListener(this);
        itemView.setOnClickListener(this);

    }

    @Override
    public void bind(Receive receive) {

        tvName.setText(receive.getName()
                .concat(" ")
                .concat(String.valueOf(receive.getQuantity()))
                .concat(" ").concat(receive.getUnit())
        );

        tvSupplier.setText("Supplier: ".concat(receive.getReceived_from().getName()));

    }

    @Override
    public void onClick(View view) {
        if(view==itemView){
            getListener().onItemClick(getAdapterPosition());
        }else if(view==ivEdit){
            getListener().onItemUpdate(getAdapterPosition());
        }else if(view==ivDelete){
            getListener().onItemRemove(getAdapterPosition());
        }else if(view==ivAttach){
            getListener().onImageClick(getAdapterPosition());
        }
    }
}
