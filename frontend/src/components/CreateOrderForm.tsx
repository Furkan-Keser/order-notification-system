import { useState } from 'react'
import { createOrder } from '../api'

interface Props {
  onCreated: () => void
  onCancel: () => void
}

function CreateOrderForm({
  onCreated,
  onCancel,
}: Props) {
  const [customerName, setCustomerName] = useState('')
  const [customerEmail, setCustomerEmail] = useState('')
  const [totalAmount, setTotalAmount] = useState('')

  const [saving, setSaving] = useState(false)
  const [error, setError] = useState('')

  async function handleSubmit(
    event: React.FormEvent<HTMLFormElement>,
  ) {
    event.preventDefault()

    try {
      setSaving(true)
      setError('')

      await createOrder({
        customerName,
        customerEmail,
        totalAmount: Number(totalAmount),
      })

      onCreated()
    } catch (err) {
      if (err instanceof Error) {
        setError(err.message)
      } else {
        setError('Beklenmeyen bir hata oluştu.')
      }
    } finally {
      setSaving(false)
    }
  }

  return (
    <div className="modal-backdrop">
      <div className="modal">
        <div className="modal-header">
          <div>
            <h2>Yeni Sipariş</h2>
            <p>Yeni müşteri siparişi oluştur.</p>
          </div>

          <button
            type="button"
            className="close-button"
            onClick={onCancel}
          >
            ×
          </button>
        </div>

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Müşteri Adı</label>

            <input
              type="text"
              value={customerName}
              onChange={(event) =>
                setCustomerName(event.target.value)
              }
              placeholder="Örn. Furkan Keser"
              required
            />
          </div>

          <div className="form-group">
            <label>E-posta</label>

            <input
              type="email"
              value={customerEmail}
              onChange={(event) =>
                setCustomerEmail(event.target.value)
              }
              placeholder="furkan@example.com"
              required
            />
          </div>

          <div className="form-group">
            <label>Toplam Tutar</label>

            <input
              type="number"
              min="0.01"
              step="0.01"
              value={totalAmount}
              onChange={(event) =>
                setTotalAmount(event.target.value)
              }
              placeholder="1250.50"
              required
            />
          </div>

          {error && (
            <div className="form-error">
              {error}
            </div>
          )}

          <div className="form-actions">
            <button
              type="button"
              className="secondary-button"
              onClick={onCancel}
            >
              Vazgeç
            </button>

            <button
              type="submit"
              className="primary-button"
              disabled={saving}
            >
              {saving
                ? 'Oluşturuluyor...'
                : 'Sipariş Oluştur'}
            </button>
          </div>
        </form>
      </div>
    </div>
  )
}

export default CreateOrderForm