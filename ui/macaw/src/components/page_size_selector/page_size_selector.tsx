import { NativeSelect } from "@chakra-ui/react"

interface PageSizeSelectorProps {
  value: string | number;
  onChange: (value: string | number) => void;
}

const PageSizeSelector = ({ value, onChange }: PageSizeSelectorProps) => {

  return (
    <NativeSelect.Root 
        size="xs" 
    >
      <NativeSelect.Field
        value={String(value)}
        onChange={(e) => onChange(e.target.value)}
      >
        <option value="10">10</option>
        <option value="20">20</option>
        <option value="50">50</option>
        <option value="100">100</option>
      </NativeSelect.Field>
      <NativeSelect.Indicator />
    </NativeSelect.Root>
  )
}

export default PageSizeSelector