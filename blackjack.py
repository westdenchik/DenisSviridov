import random
from tkinter import *
koloda = [6,7,8,9,10,2,3,4,11]*4
random.shuffle(koloda)

print('lets play')
count=0
while True:
     choice=input('will u take card? y or n\n')
     if choice == 'y':
         current = koloda.pop()
         print('your card is %d\n'%current)
         count+=current
         if count >21:
             print('u lost')
             break
         elif count==21:
             print('u earn 21!')
             break
         else:
             print('u have %d score' %count)
     elif choice=='n':
         print('u have %d score and u finishes' %count)
         break
print('goodbye!!')