import pandas as pd
import numpy as np

def read(filename):
    df = pd.read_csv(filename, header = None)
    df = df.values
    m = len(df)
    arr = df[:,df.shape[1]-1]
    arr = arr.astype(int)
    final_df = np.zeros((m, 10))
    final_df[np.arange(m),arr] = 1               #make one hot encoded matrix
    x = df[:, :df.shape[1]-2]
    x = np.hstack((np.ones((m,1)), x))
    y = final_df[:, :10]
    return x, y

def readnolabel(filename):
    df = pd.read_csv(filename, header = None)
    df = df.values
    x = df[:,:]
    return x

def softmax(x, X):
    return np.exp(x)/np.sum(np.exp(X))

class mlr:
	def __init__(self, batch_size, num_inputs, num_outputs, learning_rate, activation_func, non_linearity = "softmax", variable_learning_rate = 0, tol = 0.0001):
		self.batch_size = batch_size
		self.num_inputs = num_inputs
		self.num_outputs = num_outputs
		self.layers_size = [num_inputs] + [num_outputs]
		self.num_layers = 1
		self.w = [(np.random.rand(self.layers_size[i+1], self.layers_size[i]+1) * 2 - 1)/10 for i in range(self.num_layers)]
		self.learning_rate = learning_rate
		self.activation_func = activation_func
		self.non_linearity = non_linearity
		self.variable_learning_rate = variable_learning_rate
		self.tol = tol

	def calc_loss(self, X, Y):
		ans = X.T
		print(ans.shape)
		for i in range(self.num_layers):
			ans = self.activation_func(np.dot(self.w[i], np.insert(ans,0,1,axis = 0)),X)
		ans = ans.T
		return ((np.sum((ans - Y)**2) / len(Y)))

	def calc_accuracy(self, X, Y):
		ans = X.T
		for i in range(self.num_layers):
			ans = self.activation_func(np.dot(self.w[i], np.insert(ans, 0, 1, axis = 0)),X)
		predicted = np.argmax(ans, axis = 0)
		actual = np.argmax(Y, axis = 1)
		accuracy = 1 - (np.count_nonzero(actual - predicted)/float(len(actual)))
		return accuracy*100

	def test(self, X):
		ans = X.T
		for i in range(self.num_layers):
			ans = self.activation_func(np.dot(self.w[i], np.insert(ans, 0, 1, axis = 0)),X)
		predicted = np.argmax(ans, axis = 0)
		return predicted    


	def train(self, X, Y):
		m = X.shape[0]	
		epoch_number = 0
		stop = 0
		new_loss = self.calc_loss(X, Y)
		while ( epoch_number < 10 and stop == 0):
			print("epoch: " + str(epoch_number))
#			print("accuracy: " + str(self.calc_accuracy(X, Y)))
			epoch_number += 1

			current_loss = new_loss
			if (m%self.batch_size == 0):
				num_batches = int(m/self.batch_size)
			else:
				num_batches = int(m/self.batch_size) + 1
			for batch_num in range(num_batches):
				X_working = X[batch_num*self.batch_size: min(m, (batch_num+1)*self.batch_size), :]
				Y_working = Y[batch_num*self.batch_size: min(m, (batch_num+1)*self.batch_size), :]
		
				outputs = [None for i in range(self.num_layers + 1)]
				outputs[0] = X_working.T
                
			for i in range(self.num_layers):
			    outputs[i+1] = self.activation_func(np.dot(self.w[i], np.insert(outputs[i], 0, 1, axis = 0)),X)   #inserted 1 so that matrix dimension confirms
			errors = [None for i in range(self.num_layers)]
			errors[self.num_layers - 1] = (outputs[self.num_layers] - Y_working.T)
			for i in range(self.num_layers-2, -1, -1):
				errors[i] = np.multiply(np.dot(self.w[i+1].T[1:], errors[i+1]), np.multiply(outputs[i+1],(1-outputs[i+1])))
			gradients = [None for i in range(self.num_layers)]
			for i in range(self.num_layers-1, -1, -1):
				gradients[i] = np.dot(errors[i], np.insert(outputs[i].T, 0, 1, axis = 1))
				self.w[i] = self.w[i] - self.learning_rate * gradients[i]
			new_loss = self.calc_loss(X, Y)
#			print("new loss : " + str(new_loss))

			if (abs(new_loss - current_loss) < 0.000001):
				stop = 1
        
def answer():
    x_train, y_train = read("/Users/arundhatidixit/Desktop/Course/ML/Assignment2/glass.csv")

    non_linearity = softmax
    function = softmax
    variable_learning_rate = 0
    batch_size = 1
    input_size = 9
    output_size = 10
    anss = mlr(batch_size, input_size, output_size, 0.00085, function, non_linearity, variable_learning_rate)
    anss.train(x_train, y_train)
    print ('loading test')
    predicted_test = anss.test(x_train)
    print ('done')
    idarr = np.arange(214) + 1
    final = np.column_stack((idarr, predicted_test))
    names = ['id','label']
    df = pd.DataFrame(final, columns=names)
    df.to_csv('df.csv', index=False, header=True, sep=',')
    return final

answer()