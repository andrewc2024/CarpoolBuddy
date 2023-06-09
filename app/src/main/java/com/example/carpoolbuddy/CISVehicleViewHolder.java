package com.example.carpoolbuddy;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.concurrent.TimeoutException;


public class CISVehicleViewHolder extends RecyclerView.ViewHolder {
    protected TextView vehicleModel;
    protected TextView EVG;
    protected TextView price;
    protected TextView plate;
    protected Button bookRide;
    protected TextView seatsRemaining;
    public CISVehicleViewHolder(@NonNull View itemView) {
        super(itemView);
        vehicleModel = itemView.findViewById(R.id.vehicleModel);
        plate = itemView.findViewById(R.id.licensePlate);
        price = itemView.findViewById(R.id.price);
        bookRide = itemView.findViewById(R.id.bookSeat);
        seatsRemaining = itemView.findViewById(R.id.avaliSeats);
        EVG = itemView.findViewById(R.id.EVG);


    }
}
