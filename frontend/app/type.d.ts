type Profile = {
  accountId: string,
  username: string,
  roles: string,
  balance: number
}

export type Params = {
  params: {
    id: number
  }
}

type Account = {
  accountId: string,
  username: string,
  balance: number
}

type Transaction = {
  amountTransfer: number,
  sourceId: string,
  destinationId: string,
  createdAt: string,
  transactionId: number
}