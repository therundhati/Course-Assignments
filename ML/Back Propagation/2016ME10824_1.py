import pandas as pd
import numpy as np
#from datetime import datetime
#import matplotlib.pyplot as plt
#import seaborn as sn
#from sklearn.metrics import confusion_matrix
#import time
#import sys

def read(filename):
#	start = datetime.now()
    df = pd.read_csv(filename, header = None)
    df = df.values                                #enables numpy operations on dataframe values
    m = len(df)                                   #number of data points
    arr = df[:,df.shape[1]-1]
    arr = arr.astype(int)                                #first column is labels, take for one hot encoding
    final_df = np.zeros((m, 10))
#    final_df = np.asfarray(final_dff) + 0.01
#    final_df = np.asfarray(final_dff) + 0                  
#    final_df[np.arange(m), arr] = 0.99
    final_df[np.arange(m),arr] = 1               #make one hot encoded matrix with 0.01 and 0.99 and not 0 and 1
#    fac = 1
    #map these values into [0.01, 1], add 0.01 to avoid 0 as inputs, which may prevent weight update
    x = df[:, 0:25]
    x = np.hstack((np.ones((m,1)), x))
#    x_edit = np.asfarray(x) * fac
    y = final_df[:, :10]
    return x, y


def readnolabel(filename):
    df = pd.read_csv(filename, header = None)
    df = df.values
#    df = df.to_numpy()
#    fac = 1
    x = df[:,:]
#    x_edit = np.asfarray(x) * fac
    return x

def sigmoid(x):
  return 1 / (1 + np.exp(-x))

def tanh(x):
    return (np.exp(x)-np.exp(-x)) / (np.exp(x)+np.exp(-x))

class NeuralNetwork:
	def __init__(self, batch_size, num_inputs, num_outputs, hidden_layers, learning_rate, activation_func, non_linearity = "sigmoid", variable_learning_rate = 0, tol = 0.0001):
		print(hidden_layers)
		self.batch_size = batch_size
		self.num_inputs = num_inputs
		self.num_outputs = num_outputs
		self.layers_size = [num_inputs] + hidden_layers + [num_outputs]
		self.num_layers = len(hidden_layers) + 1
		self.w = [(np.random.rand(self.layers_size[i+1], self.layers_size[i]+1) * 2 - 1)/7 for i in range(self.num_layers)]
        #initialise weights
		self.learning_rate = learning_rate
		self.activation_func = activation_func
		self.non_linearity = non_linearity
		self.variable_learning_rate = variable_learning_rate
		self.tol = tol

	def calc_loss(self, X, Y):
		ans = X.T
		print(ans.shape)
		for i in range(self.num_layers):
			ans = self.activation_func(np.dot(self.w[i], np.insert(ans,0,1,axis = 0)))
		ans = ans.T
		return ((np.sum((ans - Y)**2) / len(Y)))

	def calc_accuracy(self, X, Y):
		ans = X.T
		for i in range(self.num_layers):
			ans = self.activation_func(np.dot(self.w[i], np.insert(ans, 0, 1, axis = 0)))
		predicted = np.argmax(ans, axis = 0)
		actual = np.argmax(Y, axis = 1)		# row wise max
		accuracy = 1 - (np.count_nonzero(actual - predicted)/float(len(actual)))
		return accuracy*100

	def test(self, X):
		ans = X.T
		for i in range(self.num_layers):
			ans = self.activation_func(np.dot(self.w[i], np.insert(ans, 0, 1, axis = 0)))
		predicted = np.argmax(ans, axis = 0)
		return predicted    


	def train(self, X, Y):
#		start = time.time()
		m = X.shape[0]                  #number of data points in x		
		epoch_number = 0                #multiple training iterations of training set
		stop = 0
		new_loss = self.calc_loss(X, Y)
		# for epoch_number in range(1000):
		while ( epoch_number < 5000 and stop == 0):
			print("epoch: " + str(epoch_number))
			print("accuracy: " + str(self.calc_accuracy(X, Y)))
			epoch_number += 1

			current_loss = new_loss
			# print("old loss : " + str(current_loss))

			# divide into batches and each batch performs one update
			if (m%self.batch_size == 0):
				num_batches = int(m/self.batch_size)
			else:
				num_batches = int(m/self.batch_size) + 1
			for batch_num in range(num_batches):
				X_working = X[batch_num*self.batch_size: min(m, (batch_num+1)*self.batch_size), :]
				Y_working = Y[batch_num*self.batch_size: min(m, (batch_num+1)*self.batch_size), :]
		
				outputs = [None for i in range(self.num_layers + 1)]		# +1 for hidden layer
				outputs[0] = X_working.T
                
				# FORWARD PROPOGATION
			for i in range(self.num_layers):
			    outputs[i+1] = self.activation_func(np.dot(self.w[i], np.insert(outputs[i], 0, 1, axis = 0)))   #inserted 1 so that matrix dimension confirms

				# BACK PROPOGATION
			errors = [None for i in range(self.num_layers)]
#            errors = [0] * self.num_layers
#			errors[self.num_layers - 1] = np.multiply((outputs[self.num_layers] - Y_working.T) , np.multiply(outputs[self.num_layers], (1 - outputs[self.num_layers])))
			errors[self.num_layers - 1] = (outputs[self.num_layers] - Y_working.T)
			for i in range(self.num_layers-2, -1, -1):
				errors[i] = np.multiply(np.dot(self.w[i+1].T[1:], errors[i+1]), np.multiply(outputs[i+1],(1-outputs[i+1])))
			gradients = [None for i in range(self.num_layers)]
			for i in range(self.num_layers-1, -1, -1):
				gradients[i] = np.dot(errors[i], np.insert(outputs[i].T, 0, 1, axis = 1))
				self.w[i] = self.w[i] - self.learning_rate * gradients[i]
			new_loss = self.calc_loss(X, Y)
#			print("new loss : " + str(new_loss))

			if (abs(new_loss - current_loss) < 0.00000000001):
				stop = 1
#		end = time.time()
#		return (end - start)
        
def answer():
    x_train, y_train = read("/Users/arundhatidixit/Desktop/Course/ML/Assignment1/ell409demopart1/trainData.csv")
#    x_train, y_train = read("/Users/arundhatidixit/Desktop/mnist_train.csv")
    x_test = readnolabel("/Users/arundhatidixit/Desktop/Course/ML/Assignment1/ell409demopart1/testData.csv")

    non_linearity = sigmoid
    function = sigmoid
    variable_learning_rate = 0
    batch_size = 250
    #200: 73     250: 76     300: 7
    input_size = 25
    output_size = 10
    layers = [225]
    # 80: 72   75, 70: 73.4    #75: 65   #100: 66  #250 78.26
    NN = NeuralNetwork(batch_size, input_size, output_size, layers, 0.00085, function, non_linearity, variable_learning_rate)   #0.015: 77.4
#    start = time.time()
    print ('loading training')
    NN.train(x_train, y_train)
    print ('loading done')
#    end = time.time()
    print ('loading test')
#    predicted_train = NN.test(x_train)
#    actual_train = y_train
    predicted_test = NN.test(x_test)
    print ('done')
    idarr = np.arange(3000) + 1
#    idarr = idarr.astype(str)
#    predicted_test = predicted_test.astype(str)
    final = np.column_stack((idarr, predicted_test))
#    temp = np.array(['id', 'label'])
#    finalprint = final.astype(str)
#    stringadd = (('id','label'))
#    finalprint = stringadd + finalprint
    names = ['id','label']
    df = pd.DataFrame(final, columns=names)
    df.to_csv('df.csv', index=False, header=True, sep=',')
    
#    f = open('otp','w')
#    f.writelines(final)
#    np.savetxt("result.csv", finalprint, fmt="%s", delimiter=",")
    
#    acc_train = 1 - (np.count_nonzero(actual_train-predicted_train)/float(len(actual_train)))
#    print (acc_train)
    return final
    
answer()