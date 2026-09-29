import type { Order, OrderStatus } from './types'

export interface CreateOrderRequest {
  customerName: string
  customerEmail: string
  totalAmount: number
}

export async function getOrders(): Promise<Order[]> {
  const response = await fetch('/api/orders')

  if (!response.ok) {
    throw new Error('Siparişler alınamadı.')
  }

  return response.json()
}

export async function createOrder(
  request: CreateOrderRequest,
): Promise<Order> {
  const response = await fetch('/api/orders', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(request),
  })

  if (!response.ok) {
    const errorData = await response.json().catch(() => null)

    throw new Error(
      errorData?.message ?? 'Sipariş oluşturulamadı.',
    )
  }

  return response.json()
}

export async function updateOrderStatus(
  orderId: number,
  status: OrderStatus,
): Promise<Order> {
  const response = await fetch(
    `/api/orders/${orderId}/status`,
    {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ status }),
    },
  )

  if (!response.ok) {
    const errorData = await response.json().catch(() => null)

    throw new Error(
      errorData?.message ??
        'Sipariş durumu güncellenemedi.',
    )
  }

  return response.json()
}