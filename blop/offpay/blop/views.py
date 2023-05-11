#-------------------------------------------------------------------------
#------------------------------------------------------------#
# MINI PROJECT
# SASTRA UNIVERSITY
# CSE CYBER SECURITY AND BLOCKCHAIN TECHNOLOGY
# D KARTHIK SAINADH REDDY
# A YASWANTH
# A SAI TARUN REDDY
#------------------------------------------------------------#

from django.shortcuts import render
from django.http import HttpResponse




#--------- IMPORTING NECESSARY LIBRARIES FOR -----------#
#--------- IMPLEMENTING BLOCKCHAIN IN PYTHON -----------#
import json
import hashlib
import time
#------------------------------------------------------------#

#----------GLOBAL VARIABLES----------------------------------#
proof_of_authority = False
#------------------------------------------------------------#

#---------DECALARATION OF ABSTRACT DATA TYPES----------------#
class Block:
    """
    PURPOSE:
        declaration of what data types each block should hold for blockchain
    """
    def __init__(self, index, timestamp, transactions, previous_hash, nonce=0):
        """
            CONSTRUCTOR : 
        """
        self.index = index
        self.timestamp = timestamp
        self.transactions = transactions
        self.previous_hash = previous_hash
        self.nonce = nonce

    def compute_hash(self):
        """
            Purpose: use sha256 and generate hash to the block
        """
        block_string = json.dumps(self.__dict__, sort_keys=True)
        return hashlib.sha256(block_string.encode()).hexdigest()

    def __str__(self):
        return f"Block {self.index} [{self.timestamp}] (Nonce: {self.nonce})"

    def __repr__(self):
        return str(self)
#------------------------------------------------------------#


class Blockchain:
    """
        PURPOSE : Defining the properties of our blockchain
    """
    def __init__(self, filename="blop/blockchain.json"):
        """
            Constructor : initialise the chain
        """
        #---------------------------------------------#
        self.chain = []
        self.filename = filename
        self.load()
        if not self.chain:
            self.create_genesis_block()
        #---------------------------------------------#
        return

    def create_genesis_block(self):
        """
        Purpose : to generate the genesis block of the blockchain which is the first block of blockchain
        """
        #---------------------------------------------#
        self.chain.append(Block(0, time.time(), [], "0"))
        #---------------------------------------------#
        return 
    
    def add_block(self, block):
        """
            Purpose:  add new block to the existing chain
        """
        #---------------------------------------------#
        block.previous_hash = self.chain[-1].compute_hash()
        block.index = len(self.chain)
        self.chain.append(block)
        #---------------------------------------------#
        return

    def load(self):
        """
        Purpose : load JSON file and update block
        """
        #---------------------------------------------#
        try:
            with open(self.filename, "r") as file:
                data = json.load(file)
                self.chain = [
                    Block(
                        block_data["index"],
                        block_data["timestamp"],
                        block_data["transactions"],
                        block_data["previous_hash"],
                        block_data["nonce"],
                    )
                    for block_data in data["chain"]
                ]
        except:
            pass
        #---------------------------------------------#
        return

    def save(self):
        """
        Purpose: save that modified chain to JSON file
        """
        #---------------------------------------------#
        with open(self.filename, "w") as file:
            data = {"chain": [block.__dict__ for block in self.chain]}
            json.dump(data, file, cls=BlockEncoder)
        #---------------------------------------------#

    def is_valid(self):
        """
        Purpose : chain validation
        """
        #---------------------------------------------#
        for i in range(1, len(self.chain)):
            block = self.chain[i]
            prev_block = self.chain[i - 1]
            if block.compute_hash() != block.hash or block.previous_hash != prev_block.compute_hash():
                return False
        #---------------------------------------------#
        return True

    def mine_block(self, miner_address,poa=False):
        """
        Purpose : mining function, mines the block based on consensus algorithm
        """
        #----------- global variables --------------------#
        #----------- PoA ----------------------------------#
        if(poa == True):
            if miner_address in ["Auto Mining"]:
            # Generate a new block
                transactions = self.get_pending_transactions()
                block = Block(len(self.chain), time.time(), transactions, self.chain[-1].compute_hash())
                nonce = 0
                block.nonce = nonce
                self.add_block(block)
                self.save()
                print(f"Mined block {block.index} (Nonce: {nonce})")
                print(f"Block hash: {block.compute_hash()}")
                reward_transaction = {"from": "network", "to": miner_address, "amount": 0}
                self.add_transaction(None, miner_address, reward_val, reward_transaction)
            return
        #-------------- conventional mining----------------#
        if(miner_address == "network"):
            pass
        else:
            print("miner is : ", miner_address)
        reward_val = 0
        transactions = self.get_pending_transactions()
        block = Block(len(self.chain), time.time(), transactions, self.chain[-1].compute_hash())
        nonce = 0
        while not self.valid_nonce(block, nonce):
            nonce += 1
        block.nonce = nonce
        self.add_block(block)
        self.save()
        print(f"Mined block {block.index} (Nonce: {nonce})")
        print(f"Block hash: {block.compute_hash()}")
        reward_transaction = {"from": "network", "to": miner_address, "amount": 1}
        self.add_transaction(None, miner_address, reward_val, reward_transaction)
        #---------------------------------------------#

    def get_balance(self, address):
        balance = 0
        for block in self.chain:
            for transaction in block.transactions:
                if transaction["from"] == address:
                    balance -= transaction["amount"]
                if transaction["to"] == address:
                    balance += transaction["amount"]
        return balance

    def add_transaction(self, sender_address, recipient_address, amount, data=None):
        transaction = {"from": sender_address, "to": recipient_address, "amount": amount, "data": data}
        self.chain[-1].transactions.append(transaction)
        self.save()
        print(sender_address,recipient_address,amount)
        if(recipient_address != "network"):
            update_wallet(sender_address,recipient_address,amount)

    def get_pending_transactions(self):
        transactions = []
        for block in self.chain:
                # transactions.extend(block.transactions)
                transactions = block.transactions
        return transactions

    def valid_nonce(self, block, nonce):
        block.nonce = nonce
        guess_hash = block.compute_hash()
        return guess_hash[:4] == "0000"
    
    def print_chain(self):
        print(self.chain)

class BlockEncoder(json.JSONEncoder):
    def default(self, obj):
        if isinstance(obj, Block):
            return obj.dict
        return json.JSONEncoder.default(self, obj)
    
def update_wallet(sender,reciever,amount):
    with open('blop/wallets.json', 'r') as file:
        data = json.load(file)
        if(sender not in data.keys()):
            data[sender]=0
        if(reciever not in data.keys()):
            data[reciever]=0
        data[sender] -= amount 
        data[reciever] += amount
        print("wallet data : ")
        print(data)
    with open('blop/wallets.json', 'w') as file:
        json.dump(data, file, indent=4)    

def update_transaction(sender,reciever,amount,timestamp):
    with open("blop/transactions.txt",'r') as file:
        data = file.read()
        data += "sender-"+sender+"reciever-"+reciever+"amount-"+str(amount)+"timestamp-"+timestamp+"||"
        data = data.split("||")
        data = list(set(data))
        data = "||".join(data)
        data=str(data)
    with open("blop/transactions.txt",'w') as file:
        file.write(data)

def adds_transaction(sender,reciever,amount,timestamp,chain):
    chain.add_transaction(sender,reciever,amount)
    update_transaction(sender,reciever,amount,timestamp)

def init_mining(miner,reward,chain):
    chain.mine_block(miner,reward)



# Create your views here.
def home_page(request):
    return render(request, 'index.html')

def pay_page(request):
    return render(request, 'paypage.html')

def sell_page(request):
    return render(request, 'sellpage.html')

def transaction_page(request):
    return render(request, 'transactionpage.html')

def data_display(request):
    with open("blop/transactions.txt") as file:
        data = file.read()
        data = data.replace("||","\n")
        data = "transactions : ................"+data
    with open("blop/blockchain.json", 'r') as file:
        bcdata = json.load(file)
        data = data+"\n.............blockchain data..........."+str(bcdata)
    return HttpResponse(data)

def test(request):
    blockchain = Blockchain()
    blockchain.mine_block("network")
    transactiondata = request.GET['a']
    a = transactiondata[1:-1:].split("||")
    tns = []
    for transaction in a:
        i = transaction.split("-")
        sender = i[0]
        reciever = i[1]
        amount = int(i[2])
        timestamp = i[3]
        temp = [sender,reciever,amount,timestamp]
        tns.append(temp)
        adds_transaction(sender,reciever,amount,timestamp,chain=blockchain)
        init_mining("network",0,chain=blockchain) 
    return HttpResponse("updated transactions")