export type OrderStatus =
  | 'CREATED'
  | 'PREPARING'
  | 'SHIPPED'
  | 'DELIVERED'
  | 'CANCELLED'

export interface Order {
  id: number
  customerName: string
  customerEmail: string
  totalAmount: number
  status: OrderStatus
  createdAt: string
}