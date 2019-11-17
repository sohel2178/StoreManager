package com.forbitbd.storeapp.ui.store.supplier.supplier_detail.summery;

import com.forbitbd.storeapp.models.Receive;
import com.forbitbd.storeapp.models.Summery;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;

public class SummeryPresenter implements SummeryContract.Presenter {

    private SummeryContract.View mView;

    public SummeryPresenter(SummeryContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void groupByName(List<Receive> receiveList) {
        Observable.fromIterable(receiveList)
                .groupBy(new Function<Receive, String>() {
                    @Override
                    public String apply(Receive receive) throws Exception {
                        return receive.getName();
                    }
                })
                .subscribe(new Consumer<GroupedObservable<String, Receive>>() {
                    @Override
                    public void accept(GroupedObservable<String, Receive> group) throws Exception {
                        final Summery summery = new Summery(group.getKey());

                        group.subscribe(new Consumer<Receive>() {
                            @Override
                            public void accept(Receive receive) throws Exception {
                                if(summery.getUnit()==null){
                                    summery.setUnit(receive.getUnit());
                                }
                                summery.add(receive.getQuantity());
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                            }
                        }, new Action() {
                            @Override
                            public void run() throws Exception {
                                mView.addToAdapter(summery);
                            }
                        });
                    }
                });
    }
}
