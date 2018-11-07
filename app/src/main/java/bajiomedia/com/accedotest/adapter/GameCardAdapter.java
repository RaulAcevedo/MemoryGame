package bajiomedia.com.accedotest.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

import bajiomedia.com.accedotest.R;

/**
 * Created by Raúl on 11/10/2015.
 */
public class GameCardAdapter extends ArrayAdapter <Integer> {

    private ArrayList<Integer> cards;
    public GameCardAdapter(Context context ){
        super(context,0);
        cards = shuffleItems(16);
    }

    private ArrayList<Integer> shuffleItems (int totalItems){
        ArrayList<Integer> items = new ArrayList<>();
        Random rd = new Random();
        int number;

        for(int i=1; i<= totalItems; i++){
            do {
                number = rd.nextInt(totalItems) + 1;
            }while(items.indexOf(number) !=  -1);
            items.add(number);
        }
        return items;
    }

    @Override
    public Integer getItem(int position) {
        if(cards != null)
            return cards.get(position);
        return null;
    }

    @Override
    public int getCount() {
        if(cards != null)
            return cards.size();
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.card_item,parent,false);
        CardDescriptor descriptor = new CardDescriptor();
        descriptor.setCode(getItem(position) % 8);
        view.setTag(descriptor);
        ImageView symbol = (ImageView)view.findViewById(R.id.card_symbol);
        switch(descriptor.getCode()){
            case 0:
                symbol.setImageResource(R.drawable.colour8);
                break;

            case 1:
                symbol.setImageResource(R.drawable.colour1);
                break;

            case 2:
                symbol.setImageResource(R.drawable.colour2);
                break;

            case 3:
                symbol.setImageResource(R.drawable.colour3);
                break;

            case 4:
                symbol.setImageResource(R.drawable.colour4);
                break;

            case 5:
                symbol.setImageResource(R.drawable.colour5);
                break;

            case 6:
                symbol.setImageResource(R.drawable.colour6);
                break;

            case 7:
                symbol.setImageResource(R.drawable.colour7);
                break;

        }
        return view;
    }

    public class CardDescriptor{
        private int code;
        private boolean flip;

        public CardDescriptor(){
            code = 0;
            flip = false;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public boolean isFlip() {
            return flip;
        }

        public void setFlip(boolean flip) {
            this.flip = flip;
        }
    }
}
