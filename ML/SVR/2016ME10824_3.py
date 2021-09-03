# conda install -c conda-forge cvxopt
# pip install libsvm
# pip install sklearn

import pandas as pd
#import numpy as np
#import matplotlib.pyplot as plt
from sklearn.svm import SVR
from sklearn import preprocessing

def read(filename):
    df = pd.read_csv(filename, header = None)
    df = df.values 
#    m = len(df)                                   #number of data points
    y = df[:,df.shape[1]-1]                       #last column is labels, take for one hot encoding
    x = df[:, 0:13]                               #13 features are x
    return x, y

def answer():
    x, y = read("/Users/arundhatidixit/Desktop/Course/ML/Assignment3/BostonHousing.csv")
#    plt.plot(x[:,:1], y)
#    print (y)
    x = preprocessing.scale(x)
#    rbfk = SVR(kernel='rbf', C = 1000, epsilon = 10)
  #  link = SVR(kernel='linear', C = 5, epsilon = 9)
    polyk = SVR(kernel='poly', C = 1000, degree = 5, epsilon = 10)
 #   rbfk.fit(x, y)
  #  link.fit(x, y)
    polyk.fit(x, y)
#    print (rbfk.get_params(deep=True))
   # print (rbfk.score(x, y))
   # print (link.score(x, y))
    print (polyk.score(x, y))
    return

answer()