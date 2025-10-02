"use client"

import { useSearchParams } from "next/navigation"

type Props = {
  number: number,
  size: number,
  totalPages: number,
  totalElements: number,
  numberOfElements: number
}

export function SearchToolbar(
  { data }: { data: Props }
) {
  const searchParams = useSearchParams()
  const keyword = searchParams.get('keyword') ?? ""

  const {
    number, size, totalPages, totalElements, numberOfElements
  } = data

  const itemsLength = size * number

  return (
    <form style={{ overflow: 'auto', marginTop: '1rem' }}>
      <input name="keyword" type="text" placeholder="search" defaultValue={keyword} />
      <label htmlFor="page">
        {numberOfElements ?
          <>
            {itemsLength + 1}-{numberOfElements} items from {totalElements}
          </> : <></>
        }
        <select
          name="page"
          id="page"
          defaultValue={number + 1}
        >
          {Array.from({ length: totalPages }).map((_, i) =>
            <option
              key={i}
              value={i + 1}>
              {i + 1}
            </option>
          )}
        </select>
      </label>
      <input type="submit" value={'search'} />
    </form>
  )
}