package com.cmbc.android.service_assistant.api;

import com.cmbc.android.service_assistant.R;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class MyCustomDialog extends Dialog {

	public MyCustomDialog(Context context) {
		super(context);
	}
	
	public MyCustomDialog(Context context, int theme){
		super(context, theme);
	}

	 public static class Builder {  
	        private Context context;  
	        private String skuCode,skuName,spuCode,outQty,unit;
	        private View contentView;  ;  
	  
	        public Builder(Context context) {  
	            this.context = context;  
	        }  
	  
	        public Builder setSkuCode(String skuCode) {  
	            this.skuCode = skuCode;  
	            return this;  
	        }  
	  
	        /** 
	         * Set the Dialog skuCode from resource 
	         *  
	         * @param skuCode
	         * @return 
	         */  
	        public Builder setSkuCode(int skuCode) {  
	            this.skuCode = (String) context.getText(skuCode);  
	            return this;  
	        }  
	  
	        public Builder setSkuName(int skuName) {  
	            this.skuName = (String) context.getText(skuName);  
	            return this;  
	        }  
	  
	
	        public Builder setSkuName(String skuName) {  
	            this.skuName = skuName;  
	            return this;  
	        }  
	        
	        public Builder setSpuCode(String spuCode){
	        	this.spuCode = spuCode;
	        	return this;
	        }
	        
	        public Builder setSpuCode(int spuCode){
	        	 this.spuCode = (String) context.getText(spuCode);  
		          return this;  
	        }
	        
	        public Builder setOutQty(String outQty){
	        	this.outQty = outQty;
	        	return this;
	        }
	        
	        public Builder setOutQty(int outQty){
	        	this.outQty = (String)context.getText(outQty);
	        	return this;
	        }
	        
	        public Builder setUnit(String unit){
	        	this.unit = unit;
	        	return this;
	        }
	        
	        public Builder setUnit(int unit){
	        	this.unit = (String)context.getText(unit);
	        	return this;
	        }
	  
	        public Builder setContentView(View v) {  
	            this.contentView = v;  
	            return this;  
	        }  
	        
	   
	  
	        public MyCustomDialog create() {  
	            LayoutInflater inflater = (LayoutInflater) context  
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
	            // instantiate the dialog with the custom Theme  
	            final MyCustomDialog dialog = new MyCustomDialog(context,R.style.goods_detail_dialog);  //对话框样式
	            View layout = inflater.inflate(R.layout.dialog_goods_detail, null);  //对话框的布局
	            dialog.addContentView(layout, new LayoutParams(  
	                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));  
	            // set the dialog skuCode  
	            ((TextView) layout.findViewById(R.id.dialog_goods_detail_tv_skucode)).setText(skuCode);  
	            ((TextView) layout.findViewById(R.id.dialog_goods_detail_tv_goodscode)).setText(spuCode);  
	            ((TextView) layout.findViewById(R.id.dialog_goods_detail_tv_outqty)).setText(outQty);  
	            ((TextView) layout.findViewById(R.id.dialog_goods_detail_tv_unit)).setText(unit);  
	            // set the confirm button  
	           
	            // set the cancel button  
	           
	            // set the content  
	            if (skuName != null) {  
	                ((TextView) layout.findViewById(R.id.dialog_goods_detail_tv_skuname)).setText(skuName);
	            } else if (contentView != null) {  
	                // if no message set  
	                // add the contentView to the dialog body  
//	                ((LinearLayout) layout.findViewById(R.id.content))  
//	                        .removeAllViews();  
//	                ((LinearLayout) layout.findViewById(R.id.content))  
//	                        .addView(contentView, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));  
	            }  
	            dialog.setContentView(layout);  
	            return dialog;  
	        }  
	    }  
}
