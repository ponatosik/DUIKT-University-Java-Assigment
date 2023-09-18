package com.bondarenko.universityAssigment.lab2.items;

import com.bondarenko.universityAssigment.lab2.Item;

public class DVD extends Item {
    /** DVD video duration (in minutes) */
    private final int duration;

    /** Initializes newly created DVD object with
     * @param title DVD title
     * @param duration DVD duration (in minutes) */
    public DVD (String title, int duration)
    {
        super(title);
        this.duration = duration;
    }

    /** @return DVD video duration (in minutes) */
    public int getDuration() {
        return duration;
    }
}
