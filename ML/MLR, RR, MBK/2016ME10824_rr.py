import pandas as pd
import numpy as np

def read(filename):
    df = pd.read_csv(filename, header = None)
    df = df.values
    m = len(df)                  #number of data points
    arr = df[:, df.shape[1]-1]    #take last column
    x = df[:, 0:df.shape[1]-1]
    y = arr
    return x, y, m

def olscoeff(x, y, m):
    xsq = np.dot(x.T, x)
    xsqinv = np.linalg.inv(xsq)
    xty = np.dot(x.T, y)
    b = np.dot(xsqinv, xty)
    return b

def mse(x, y, b, w):
    e = y - np.dot(x, b)
    return np.dot(e.T, e)

def wmse(x, y, b, w):
    earr = y - np.dot(x, b)
    e = np.dot(np.dot(np.dot(earr.T, w.T), w), earr)
    return earr, e

def rrcoeff(x, y, b, m):
    a = np.ones((m, 1))
    x = np.concatenate((a, x), axis = 1)
    b_working = b
    warr = np.ones(m)
#    w = w/np.sum(w)
    w = np.diag(warr)      #make matrix
    print (w)
    iterations = 100
    i = 1
    for i in range(iterations):
        xw = np.dot(x.T, w.T)
        xwx = np.dot(np.dot(xw, w), x)
#        print (xwx)
#        print (np.linalg.det(xwx))
        xwxinv = np.linalg.inv(xwx)
        pre = np.dot(np.dot(np.dot(xwxinv, x.T), w.T), w)
        b_working = np.dot(np.dot(pre, w), y)
        warr, e = wmse(x, y, b_working, w)    #update
        w = np.diag(warr)
        w = np.linalg.inv(w)                 #w=1/e
        print("iteration: " + str(i))
        if e <100:
            break
        i = i+1
    return b_working, w

def answer():
    x_train, y_train, m = read("/Users/arundhatidixit/Desktop/Course/ML/Assignment2/hbk.csv")
    a = np.ones((m, 1))
    x = np.concatenate((a, x_train), axis = 1)
#    print (x)
#    print (x)
#    print (y_train)
    b = olscoeff(x, y_train, m)
    br = rrcoeff(x_train, y_train, b, m)
    print (b)
    print (br)
    return

answer()