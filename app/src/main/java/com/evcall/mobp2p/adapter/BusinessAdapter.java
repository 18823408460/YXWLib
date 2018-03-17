package com.evcall.mobp2p.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avcon.bean.Business;
import com.avcon.bean.BusinessCategoriy;
import com.uurobot.yxwlib.R;

import java.util.ArrayList;
import java.util.List;

public class BusinessAdapter extends BaseAdapter {

	private Context context;
	protected LayoutInflater inflater;
	private List<Business> businessInforList=new ArrayList<>();
	private int [] imageBg=new int[]{R.mipmap.img_business_item1, R.mipmap.img_business_item2,
			R.mipmap.img_business_item3,R.mipmap.img_business_item4};
	private  OnClickItemButtonListener clickItemButtonListener;

	public BusinessAdapter(Context context, BusinessCategoriy categoriy){
		this.context=context;
		this.inflater=LayoutInflater.from(context);
		this.businessInforList.addAll(categoriy.getBusinessInforList());
	}

	@Override
	public int getCount() {
		if(businessInforList==null){
			return -1;
		}
		return businessInforList.size();
	}

	@Override
	public Object getItem(int position) {
		return businessInforList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=inflater.inflate(R.layout.list_item_business, null);
			int indexImg=position%(imageBg.length);
			int imgRes=imageBg[indexImg];
			Drawable bd=context.getResources().getDrawable(imgRes);
			convertView.setBackground(bd);
		}
		Object tag=convertView.getTag();
		Holder holder;
		final TextView name;
		final Button btn;
		final Business business=businessInforList.get(position);
		if(tag==null){
			name=(TextView) convertView.findViewById(R.id.tvName);
			btn=(Button) convertView.findViewById(R.id.btnCall);
			holder=new Holder(name,btn);
			convertView.setTag(holder);
		}else{
			holder=(Holder) tag;
			name = holder.getName();
			btn = holder.getBtn();
		}
		name.setText(business.getName());
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(clickItemButtonListener!=null){
					clickItemButtonListener.onClickBtn(business,v);
				}
			}
		});
		return convertView;
	}

	/**
	 * 设置按钮的监听
	 * @param clickItemButtonListener
     */
	public void setOnClickItemButtonListener(OnClickItemButtonListener clickItemButtonListener){
		this.clickItemButtonListener=clickItemButtonListener;
	}
	public interface OnClickItemButtonListener{
		void onClickBtn(Business business, View v);
	}
	public class Holder{
		private LinearLayout llText;
		private TextView name;
		private TextView desc;
		private Button btn;
		
		public Holder(TextView name, Button btn) {
			super();
			this.name = name;
			this.btn = btn;
		}
		public Holder( LinearLayout llText,
					  TextView name, TextView desc, Button btn) {
			super();
			this.llText = llText;
			this.name = name;
			this.desc = desc;
			this.btn = btn;
		}

		public TextView getName() {
			return name;
		}
		public Button getBtn() {
			return btn;
		}
	}
}
