import pandas as pd
import numpy as np
#import matplotlib.pyplot as plt
import math
from sklearn import preprocessing        #to normalize feature vectors
import cvxopt 
import cvxopt.solvers
from cvxopt import matrix

def read(filename):
    df = pd.read_csv(filename, header = None)
    df = df.values 
    m = len(df)                                   #number of data points
    y = df[:,df.shape[1]-1]                       #last column is labels, take for one hot encoding
    x = df[:, 0:13]                               #13 features are x
    return x, y, df, m

def split(whole, ratio, m):                  #for validation
    arr = np.random.permutation(whole)       #shuffle dataset
    nos = ratio*m
    nos = math.floor(nos)                         #GIF
    y_train = arr[0:nos, arr.shape[1]-1]                       #last column is labels, take for one hot encoding
    x_train = arr[0:nos, 0:13]
    y_test = arr[nos:m, arr.shape[1]-1]
    x_test = arr[nos:m, 0:13]
    return x_train, y_train, x_test, y_test

def kernel_lin(x, u):
    return np.dot(x.transpose(), u)

def kernel_poly(x, u, deg=4):
    return (1 + np.dot(x, u)) ** deg

def kernel_rbf(x, u, e=1):             #variance is one as all data is scaled to that
    a = np.subtract(x, u)
    e = 2*e
    power = -1*np.dot(a.transpose(), a)/e
    ans = np.exp(power)
    return ans

def svr(x_train, y_train, x_test, y_test, epsilon, c):
    K = np.zeros([len(x_train), len(x_train)])
    for i in range(len(x_train)):
        for j in range(len(x_train)):
            K[i][j]= kernel_rbf(x_train[i], x_train[j])
    K = matrix(K)
    
    ff = np.identity(2*len(x_train))*(-1)
    fff = np.identity(2*len(x_train))
    G = np.vstack((ff, fff))
    G = matrix(G)
    
    rr = np.zeros([2*len(x_train), 1])
    rrr = np.ones([2*len(x_train), 1])*c
    h = np.vstack((rr, rrr))
    h = matrix(h)
    
    ss = np.ones([1, 2*len(x_train)])*epsilon
    sss = np.hstack((y_train, -1*y_train))
    q = np.subtract(ss, sss).transpose()
    q = matrix(q)
    
    vv = np.identity(len(x_train))
    vvv = np.identity(len(x_train))*(-1)
    ii = np.vstack((vv, vvv))
    P = np.dot(np.dot(ii, K), ii.transpose())
    P = matrix(P)
    
    oo = np.ones([1, len(x_train)])
    ooo = np.ones([1, len(x_train)])*(-1)
    A = np.hstack((oo, ooo))
    A = matrix(A)
    
    b = [0.0]
    b = matrix(b)
    
    solution = cvxopt.solvers.qp(P, q, G, h, A, b)
    
    finall = np.array(solution['x'])
#    print (len(finall.transpose()))
#    print (len(finall.transpose()))
#    print (len(finall))
#    support = finall > 1e-6       #vectors having langrangians greater than (0 or near zero)
#    print (len(support))
#    indices = np.arange(len(finall))[support]
#    finall = finall[support]
#    support = x_train[support]
#    ys = y_train[support]
#    print (len(finall))            #number of support vectors
    
    y_test_predict = np.zeros([len(y_test), 1])
    for n in range(len(x_test)):
        for m in range(len(x_train)):
            K_test = np.zeros([len(x_train), 1])      #nx1
            K_test = kernel_rbf(x_train[m], x_test[n])
        st = np.identity(len(x_train))
        sts = np.identity(len(x_train))*(-1)
        stacked = np.vstack((st, sts))       #2nxn
        y_test_predict[n] = np.dot(finall.transpose(), np.dot(stacked, K_test))[0, 0]
#    print (len(y_test_predict))
    
    ym = np.mean(y_test, axis = 0)
#    print (ym)
    y_mean = np.ones([len(y_test), 1])*ym                #check accuracy and use R2 as a measure
    ess = np.dot((np.subtract(y_test_predict, y_mean)).transpose(), np.subtract(y_test_predict, y_mean))[0, 0]
    tss = np.dot((np.subtract(y_test, y_mean)).transpose(), np.subtract(y_test, y_mean))[0, 0]
    score = ess/tss
#    print (score*100)
    return score

def answer():
    x, y, whole, m = read("/Users/arundhatidixit/Desktop/Course/ML/Assignment3/BostonHousing.csv")
    x = preprocessing.scale(x)                    #to normalise feature vectors
    x = np.insert(x, 0, 1, axis = 1)              #append 1 in feature vectors for weights and b
    setr = 0.8
    x_train, y_train, x_test, y_test = split(whole, setr, m)
    epsilon = 0.1                                    #thickness of tube
    c = 1                                            #cost
    answerr = svr(x_train, y_train, x_test, y_test, epsilon, c)
#    plt.plot(x[:,:1], y)
#    print (y)
#    print (len(whole))
#    print (len(x_train))
#    print (len (x_test))
    return

answer()