// IAIDLService.aidl
package com.example.service;

// Declare any non-default types here with import statements

interface IAIDLService {
    oneway void setValue(in int value);

    int getValue();
}
