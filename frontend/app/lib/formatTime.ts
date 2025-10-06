export function formatTime(datetime: string) {
  const date = datetime.split('T')[0]
  const time = datetime.split('T')[1].split('.')[0]

  return date + ' ' + time
}