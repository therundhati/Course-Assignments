import pandas as pd
import numpy as np
from sklearn.metrics import pairwise_distances
#from sklearn.neighbors import KDTree
#import matplotlib.pyplot as plt
from matplotlib import style
style.use('ggplot')

def read(filename):
    df = pd.read_csv(filename, header = None)
    df = df.values
    m = len(df)                  #number of data points
    arr = df[:, 0]    #take last column
    x = df[:, 1:df.shape[1]]
    y = arr
    return x, y, m

def minibatch(x, batch, c):
    iteration = 13
    i = 1
    for i in range(iteration):
        x_working = x[np.random.permutation(x.shape[0])[:batch]]
        net = np.zeros(3)
        assign = np.empty(x_working.shape[0], dtype=np.int)

        for l, m in enumerate(x_working):     #assign centres to points
            assign[l] = np.argmin(((c - m)**2).sum(1))

        for p, q in enumerate(x_working):   #reassign centre
            net[assign[p]] = net[assign[p]]+1
            grad = 1.0 / net[assign[p]]
            c[assign[p]] = (1.0 - grad) * c[assign[p]] + grad*q
        i = i+1
        print (i)
        print (c)
    return c


def answer():
    x_train, y_train, m = read("/Users/arundhatidixit/Desktop/Course/ML/Assignment2/wine.csv")
    c = x_train[:3]
    batch = 15
    cminib = minibatch(x_train, batch, c)
    print (cminib)
    label = np.argmin(pairwise_distances(cminib, x_train), axis=0)
    print (label)
#    tree = KDTree(x)
#    centroids = tree.query(cminib, k=1, return_distance=False).squeeze()
#    print (centroids)
    return

answer()