package com.example.hellofx.graphiccontroller;

import com.example.hellofx.trovaprezzi.TrovaPrezziBean;

import java.util.List;

public interface VendorBoundaryInterface {
    public List<TrovaPrezziBean> fetchResults(TrovaPrezziBean trovaPrezziBean);
}
