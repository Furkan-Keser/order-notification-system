import { useEffect, useState } from 'react'
import './App.css'

import CreateOrderForm from './components/CreateOrderForm'
import {
  getOrders,
  updateOrderStatus,
} from './api'

import type {
  Order,
  OrderStatus,
} from './types'

function App() {
  const [orders, setOrders] = useState<Order[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  const [showCreateForm, setShowCreateForm] =
    useState(false)

  const [updatingOrderId, setUpdatingOrderId] =
    useState<number | null>(null)

  async function loadOrders() {
    try {
      setLoading(true)
      setError('')

      const data = await getOrders()

      setOrders(data)
    } catch (err) {
      if (err instanceof Error) {
        setError(err.message)
      } else {
        setError('Beklenmeyen bir hata oluştu.')
      }
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    loadOrders()
  }, [])

  function countStatus(
    status: OrderStatus,
  ) {
    return orders.filter(
      (order) => order.status === status,
    ).length
  }

  function formatMoney(
    amount: number,
  ) {
    return new Intl.NumberFormat(
      'tr-TR',
      {
        style: 'currency',
        currency: 'TRY',
      },
    ).format(amount)
  }

  function formatDate(
    date: string,
  ) {
    return new Intl.DateTimeFormat(
      'tr-TR',
      {
        dateStyle: 'medium',
        timeStyle: 'short',
      },
    ).format(new Date(date))
  }

  async function handleStatusUpdate(
    orderId: number,
    newStatus: OrderStatus,
  ) {
    try {
      setUpdatingOrderId(orderId)
      setError('')

      await updateOrderStatus(
        orderId,
        newStatus,
      )

      await loadOrders()
    } catch (err) {
      if (err instanceof Error) {
        setError(err.message)
      } else {
        setError(
          'Durum güncellenirken hata oluştu.',
        )
      }
    } finally {
      setUpdatingOrderId(null)
    }
  }

  return (
    <div className="app">
      <header className="header">
        <div>
          <p className="eyebrow">
            ORDER NOTIFICATION SYSTEM
          </p>

          <h1>
            Sipariş Yönetim Paneli
          </h1>

          <p className="subtitle">
            Siparişleri ve teslimat
            süreçlerini takip et.
          </p>
        </div>

        <div className="header-actions">
          <button
            className="secondary-button"
            onClick={loadOrders}
            disabled={loading}
          >
            {loading
              ? 'Yükleniyor...'
              : 'Yenile'}
          </button>

          <button
            className="primary-button"
            onClick={() =>
              setShowCreateForm(true)
            }
          >
            + Yeni Sipariş
          </button>
        </div>
      </header>

      <main>
        <section className="stats">
          <StatCard
            title="Toplam Sipariş"
            value={orders.length}
          />

          <StatCard
            title="Yeni"
            value={countStatus(
              'CREATED',
            )}
          />

          <StatCard
            title="Hazırlanıyor"
            value={countStatus(
              'PREPARING',
            )}
          />

          <StatCard
            title="Kargoda"
            value={countStatus(
              'SHIPPED',
            )}
          />

          <StatCard
            title="Teslim Edildi"
            value={countStatus(
              'DELIVERED',
            )}
          />
        </section>

        <section className="orders-panel">
          <div className="panel-header">
            <div>
              <h2>Siparişler</h2>

              <p>
                PostgreSQL veritabanında
                kayıtlı siparişler
              </p>
            </div>
          </div>

          {error && (
            <div className="error">
              {error}
            </div>
          )}

          {!loading &&
            !error &&
            orders.length === 0 && (
              <div className="empty">
                Henüz sipariş bulunmuyor.
              </div>
            )}

          {!error &&
            orders.length > 0 && (
              <div className="table-wrapper">
                <table>
                  <thead>
                    <tr>
                      <th>ID</th>

                      <th>
                        Müşteri
                      </th>

                      <th>
                        E-posta
                      </th>

                      <th>
                        Tutar
                      </th>

                      <th>
                        Durum
                      </th>

                      <th>
                        Oluşturulma
                      </th>

                      <th>
                        İşlem
                      </th>
                    </tr>
                  </thead>

                  <tbody>
                    {orders.map(
                      (order) => (
                        <tr
                          key={
                            order.id
                          }
                        >
                          <td>
                            #
                            {
                              order.id
                            }
                          </td>

                          <td className="customer">
                            {
                              order.customerName
                            }
                          </td>

                          <td>
                            {
                              order.customerEmail
                            }
                          </td>

                          <td>
                            {formatMoney(
                              order.totalAmount,
                            )}
                          </td>

                          <td>
                            <StatusBadge
                              status={
                                order.status
                              }
                            />
                          </td>

                          <td>
                            {formatDate(
                              order.createdAt,
                            )}
                          </td>

                          <td>
                            <OrderActions
                              order={
                                order
                              }
                              loading={
                                updatingOrderId ===
                                order.id
                              }
                              onUpdate={
                                handleStatusUpdate
                              }
                            />
                          </td>
                        </tr>
                      ),
                    )}
                  </tbody>
                </table>
              </div>
            )}
        </section>
      </main>

      {showCreateForm && (
        <CreateOrderForm
          onCancel={() =>
            setShowCreateForm(
              false,
            )
          }
          onCreated={() => {
            setShowCreateForm(
              false,
            )

            loadOrders()
          }}
        />
      )}
    </div>
  )
}

function StatCard({
  title,
  value,
}: {
  title: string
  value: number
}) {
  return (
    <div className="stat-card">
      <span>
        {title}
      </span>

      <strong>
        {value}
      </strong>
    </div>
  )
}

function StatusBadge({
  status,
}: {
  status: OrderStatus
}) {
  const labels: Record<
    OrderStatus,
    string
  > = {
    CREATED: 'Yeni',
    PREPARING: 'Hazırlanıyor',
    SHIPPED: 'Kargoda',
    DELIVERED: 'Teslim Edildi',
    CANCELLED: 'İptal',
  }

  return (
    <span
      className={`status status-${status.toLowerCase()}`}
    >
      {labels[status]}
    </span>
  )
}

function OrderActions({
  order,
  loading,
  onUpdate,
}: {
  order: Order
  loading: boolean
  onUpdate: (
    orderId: number,
    status: OrderStatus,
  ) => void
}) {
  if (
    order.status === 'CREATED'
  ) {
    return (
      <div className="order-actions">
        <button
          className="action-button"
          disabled={loading}
          onClick={() =>
            onUpdate(
              order.id,
              'PREPARING',
            )
          }
        >
          {loading
            ? 'İşleniyor...'
            : 'Hazırlamaya Başla'}
        </button>

        <button
          className="cancel-button"
          disabled={loading}
          onClick={() =>
            onUpdate(
              order.id,
              'CANCELLED',
            )
          }
        >
          İptal
        </button>
      </div>
    )
  }

  if (
    order.status ===
    'PREPARING'
  ) {
    return (
      <div className="order-actions">
        <button
          className="action-button"
          disabled={loading}
          onClick={() =>
            onUpdate(
              order.id,
              'SHIPPED',
            )
          }
        >
          {loading
            ? 'İşleniyor...'
            : 'Kargoya Ver'}
        </button>

        <button
          className="cancel-button"
          disabled={loading}
          onClick={() =>
            onUpdate(
              order.id,
              'CANCELLED',
            )
          }
        >
          İptal
        </button>
      </div>
    )
  }

  if (
    order.status === 'SHIPPED'
  ) {
    return (
      <button
        className="action-button"
        disabled={loading}
        onClick={() =>
          onUpdate(
            order.id,
            'DELIVERED',
          )
        }
      >
        {loading
          ? 'İşleniyor...'
          : 'Teslim Edildi'}
      </button>
    )
  }

  if (
    order.status ===
    'DELIVERED'
  ) {
    return (
      <span className="completed-text">
        Tamamlandı
      </span>
    )
  }

  return (
    <span className="cancelled-text">
      İptal edildi
    </span>
  )
}

export default App