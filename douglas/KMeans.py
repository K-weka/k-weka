#encoding: UTF-8

import random
import math
import numpy as np




"""""""""""""""""""""""""""
" Distancia euclidiana
"
"""""""""""""""""""""""""""
def euclidiana(x, y):
	distancia = 0.0
	
	for i in range(0, len(x) - 1):
		quadrado = float(x[i]) - float(y[i])
		quadrado = quadrado **2
		
		distancia += math.sqrt(quadrado)

	return distancia
		
"""""""""""""""""""""""""""
" Distancia chebyshev
"
"""""""""""""""""""""""""""
def chebyshev(x, y):
	distancia = []
	
	for i in range(0, len(x) - 1):
		absoluto = abs(float(x[i]) - float(y[i]))
		distancia.append(absoluto)
		
	return max(distancia)
	
"""""""""""""""""""""""""""
" Distancia manhattan
"
"""""""""""""""""""""""""""
def manhattan(x, y):
	distancia = 0.0
	
	for i in range(0, len(x) - 1):
		modulo = float(x[i]) - float(y[i])
		distancia += abs(modulo)

	return distancia	
	
	
	
	
	
	
	
	
	
	
		
"""""""""""""""""""""""""""
" Algoritmo Kmeans
"
"
" data - dados de entrada
" k - numero de centroides
"""""""""""""""""""""""""""
def KMeans(k):
	#carregando dataset para a tabela
	data = open('data.arff', 'r')	
	data = data.readlines()
	
	tabela = []

	for linha in data:
		linha = linha.split(',')
		objeto = []
		for i in range(0, len(linha)):
			objeto.append(linha[i])
		tabela.append(objeto)

	#selecionando centroides aleatoriamente

	centroides = []

	cont = 0
	while(cont < k):
		centroide = random.choice(tabela)
		if centroides.count(centroide) == 0:
			centroides.append(centroide)
			cont += 1
			#print "Centroide =",centroide

	# loop
	fim = False
	while not fim:

		# dividir o dataset em k clusters
		clusters = []

		for i in range(0, k):
			cluster = []
			clusters.append(cluster)
	
		#atribuindo objetos ao centroides

		for j in range(0, len(tabela)):
			menor = 9999
			cluster = -1
	
			for i in range(0, len(centroides)):
				x = tabela[j]
				y = centroides[i]
			
				distancia = manhattan(x, y)
			
				if distancia < menor:
					menor = distancia
					cluster = i	
			clusters[cluster].append(x)
		
		#recalcular os centroides
		fim = True
	
		for i in range(0, len(clusters)):
			novo = [0]
			novo = novo * 9
			for objeto in range(0, len(clusters[i])):
				for atributo in range(0, len(clusters[i][objeto]) - 1):
					instancia = clusters[i][objeto]
				
					novo[atributo] += float(instancia[atributo])
			
					if objeto == len(clusters[i]) - 1:
						novo[atributo] = novo[atributo] / len(clusters[i])
		
			#verificar se houve mudança
			if centroides[i] != novo:
				fim = False	
			centroides[i] = novo
			#print "centroide =",centroides[i]
	
	#saida do programa
	print "\nResult"
	saida = []
	for i in range(0, len(clusters)):
		print "Cluster",i, len(clusters[i]),"elementos"
		saida.append(clusters[i])
		
	saida.sort()
	return saida



def avaliar(saida):
	result = {}
	for i in range(0, len(saida)):
		for j in saida[i]:
			j = j[len(j) - 1]
			linha = j.split("\r\n")[0]
			
			if not result.has_key(i):
				result[i] = []
			result[i].append(linha)
	
	data = open('data.arff', 'r')
	
	dici = {}
	
	linhas = data.readlines()
	
	for linha in linhas:

		linha = linha.split("\r\n")[0]
		linha = linha.split(',')
		instancia = linha[len(linha) - 1]
		
		if not dici.has_key(instancia):
			dici[instancia] = 1
		else:
			dici[instancia] += 1

	dici = sorted(dici.items(), key=lambda x: x[0])
	certo = 0
	errado = 0
	#print result

	while(len(result) > 0):
		for atual in dici:
			maior = []
			chave = 0
			for i in result:
				if len(result[i]) > len(maior):
					maior = result[i]
					chave = i
			for j in maior:
				if j == atual[0]:
					certo += 1
				else:
					errado += 1	
			result.pop(chave)
	#print dici 
	print "T. de acerto",float(certo)/(certo+errado)
	return float(certo)/(certo+errado)
	#print "Acerto",certo
	#print "Errado",errado
		
		
k = 2

media = []
acertos = 0
for i in range(0, k):
	media.append([])

for i in range(0, 100):
	
	saida = KMeans(k)
	#saida = sorted(saida, key = len, reverse=True)
	acertos += avaliar(saida)
	
	for j in range(0, k):
		media[j].append(len(saida[j]))

print "\n\nTaxa de acertos:",acertos/100.0	
for i in range(0, k):
	#print media[i]
	v = np.var(media[i])
	v = np.sqrt(v)
	print "cluster %s--> %s [%s]" %(i, np.mean(media[i]), v)	
